package it.workdocuments.tests;

import it.workdocuments.project.enums.FileType;
import it.workdocuments.project.model.*;
import it.workdocuments.project.utils.ExcelUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class ExcelTests {

    private final String path = "C:\\temp";
    private final String fileName = "test_excel";
    private final String sheetName = "foglio_test";

    private List<Row> setRows(){
        List<Row> rows = new ArrayList<>();
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value("ID")
                .build());
        cells.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("PRODUCT")
                .build());
        rows.add(new Row.RowBuilder()
                .rowNumber(0)
                .cells(cells)
                .build());

        List<Cell> cells2 = new ArrayList<>();
        cells2.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value("123")
                .build());
        cells2.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("TABLE")
                .build());
        rows.add(new Row.RowBuilder()
                .rowNumber(1)
                .cells(cells2)
                .build());

        List<Cell> cells3 = new ArrayList<>();
        cells3.add(new Cell.CellBuilder()
                .cellNumber(0)
                .value("124")
                .build());
        cells3.add(new Cell.CellBuilder()
                .cellNumber(1)
                .value("CHAIR")
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
