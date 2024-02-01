package it.workdocuments.tests;

import it.workdocuments.project.enums.FileType;
import it.workdocuments.project.model.*;
import it.workdocuments.project.model.excel.Cell;
import it.workdocuments.project.model.excel.Row;
import it.workdocuments.project.model.excel.ExcelFile;
import it.workdocuments.project.model.excel.ExcelWorkSheet;
import it.workdocuments.project.model.excel.style.Alignment;
import it.workdocuments.project.model.excel.style.BackgroundColor;
import it.workdocuments.project.model.excel.style.CellStyle;
import it.workdocuments.project.model.excel.style.Font;
import it.workdocuments.project.utils.ExcelUtil;
import it.workdocuments.project.utils.FileUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExcelTests {

    private final String path = "C:\\temp";
    private final String fileName = "test_excel";
    private final String sheetName = "sheet_test";

    private List<Row> setRows(){

        CellStyle headerStyle = new CellStyle.CellStyleBuilder()
                .alignment(new Alignment.AlignmentBuilder().horizontalAlignment(HorizontalAlignment.CENTER).build())
                .borderBottom(BorderStyle.MEDIUM)
                .borderLeft(BorderStyle.MEDIUM)
                .borderRight(BorderStyle.MEDIUM)
                .backgroundColor(new BackgroundColor.BackgroundColorBuilder()
                        .fillBackgroundColor(HSSFColor.HSSFColorPredefined.GREEN)
                        .fillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN)
                        .fillPatternType(FillPatternType.SOLID_FOREGROUND)
                        .build())
                .font(new Font.FontBuilder()
                        .bold(true)
                        .color(HSSFColor.HSSFColorPredefined.RED)
                        .fontName("Arial")
                        .build())
                .build();

        CellStyle valueStyle = new CellStyle.CellStyleBuilder()
                .alignment(new Alignment.AlignmentBuilder().horizontalAlignment(HorizontalAlignment.LEFT).build())
                .borderTop(BorderStyle.THIN)
                .borderBottom(BorderStyle.THIN)
                .borderLeft(BorderStyle.THIN)
                .borderRight(BorderStyle.THIN)
                .backgroundColor(new BackgroundColor.BackgroundColorBuilder()
                        .fillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
                        .fillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
                        .fillPatternType(FillPatternType.SOLID_FOREGROUND)
                        .build())
                .font(new Font.FontBuilder()
                        .bold(false)
                        .color(HSSFColor.HSSFColorPredefined.BLUE)
                        .fontName("Courier New")
                        .build())
                .build();

        List<Row> rows = new ArrayList<>();
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value("ID")
                .headerStyle(headerStyle)
                .build());
        cells.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("PRODUCT")
                .headerStyle(headerStyle)
                .build());
        rows.add(new Row.RowBuilder()
                .rowNumber(0)
                .cells(cells)
                .build());

        List<Cell> cells2 = new ArrayList<>();
        cells2.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value(123)
                .valueStyle(valueStyle)
                .build());
        cells2.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("TABLE")
                .valueStyle(valueStyle)
                .build());
        rows.add(new Row.RowBuilder()
                .rowNumber(1)
                .cells(cells2)
                .build());

        List<Cell> cells3 = new ArrayList<>();
        cells3.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value(124)
                .valueStyle(valueStyle)
                .build());
        cells3.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("CHAIR")
                .valueStyle(valueStyle)
                .build());
        rows.add(new Row.RowBuilder()
                .rowNumber(2)
                .cells(cells3)
                .build());
        return rows;
    }
    private String writeExcel(FileType fileType) throws Exception {
        List<ExcelWorkSheet> workSheets = new ArrayList<>();
        ExcelWorkSheet workSheet = new ExcelWorkSheet();
        workSheet.setSheetName(sheetName);
        workSheet.setRows(setRows());
        workSheets.add(workSheet);

        ExcelFile excelFile = new ExcelFile.ExcelBuilder()
                .fileName(fileName)
                .fileType(fileType)
                .dirPath(path)
                .extension(fileType.label)
                .workSheets(workSheets)
                .build();

        Document<ExcelFile> document = new ExcelUtil();
        return document.writeDocument(excelFile);
    }
    private ExcelFile readExcel(FileType fileType) throws Exception {
        List<ExcelWorkSheet> workSheets = new ArrayList<>();
        ExcelWorkSheet workSheet = new ExcelWorkSheet();
        workSheet.setSheetName(sheetName);
        workSheets.add(workSheet);

        Document<ExcelFile> document = new ExcelUtil();
        return document.readDocument(new ExcelFile.ExcelBuilder()
                .fileName(fileName)
                .fileType(fileType)
                .dirPath(path)
                .extension(fileType.label)
                .workSheets(workSheets)
                .build());
    }
    @BeforeAll
    public void deleteExistingFile(){
        try {
            if(FileUtil.checkFileExists(FileUtil.combine(path, fileName + ".xls"))){
                FileUtil.deleteFile(FileUtil.combine(path, fileName + ".xls"));
            }
            if(FileUtil.checkFileExists(FileUtil.combine(path, fileName + ".xlsx"))){
                FileUtil.deleteFile(FileUtil.combine(path, fileName + ".xlsx"));
            }
        }
        catch (Exception ex){
            fail(ex);
        }
    }
    @Test
    @Order(1)
    public void writeXls() {
        try {
            String fullPath =  writeExcel(FileType.XLS);
            Assertions.assertNotNull(fullPath);
            Assertions.assertNotEquals("", fullPath);
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    @Order(2)
    public void writeXlsx() {
        try {
            String fullPath =  writeExcel(FileType.XLSX);
            Assertions.assertNotNull(fullPath);
            Assertions.assertNotEquals("", fullPath);
        }catch (Exception e){
            fail(e);
        }
    }

    @Test
    @Order(3)
    public void readXls() {
        try {
            ExcelFile excelFile =  readExcel(FileType.XLS);
            Assertions.assertNotNull(excelFile);
            Assertions.assertTrue(excelFile.getWorkSheets().size() > 0);
        }catch (Exception e){
            fail(e);
        }
    }
    @Test
    @Order(4)
    public void readXlsx() {
        try {
            ExcelFile excelFile =  readExcel(FileType.XLSX);
            Assertions.assertNotNull(excelFile);
            Assertions.assertTrue(excelFile.getWorkSheets().size() > 0);
        }catch (Exception e){
            fail(e);
        }
    }

}
