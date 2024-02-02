package it.workdocuments.project.utils;

import it.workdocuments.project.enums.FileAction;
import it.workdocuments.project.enums.FileType;
import it.workdocuments.project.model.Document;
import it.workdocuments.project.model.excel.ExcelFile;
import it.workdocuments.project.model.excel.ExcelWorkSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ExcelUtil implements Document<ExcelFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
    @Override
    public ExcelFile readDocument(ExcelFile document) throws Exception {
        try
        {
            List<ExcelWorkSheet> workSheets = new ArrayList<>();
            LOGGER.info("Start read excel file");
            String fullPath = Paths.get(document.getDirPath(), document.getFileName() + "." + document.getExtension()).toString();
            LOGGER.info("Retrieve data at the excel file from path: {}", fullPath);
            Workbook workbook = setWorkBook(document.getFileType(), FileAction.READ, fullPath);

            for (ExcelWorkSheet workSheet : document.getWorkSheets()){
                LOGGER.info("Process worksheet with name: {}", workSheet.getSheetName());
                Sheet sheet = workbook.getSheet(workSheet.getSheetName());
                List<it.workdocuments.project.model.excel.Row> rows = new ArrayList<>();
                int rowCount = 0;
                for(Row row : sheet){
                    LOGGER.info("Read line in position {}", rowCount);
                    List<it.workdocuments.project.model.excel.Cell> cells = new ArrayList<>();
                    int cellCount = 0;
                    for (Cell cell : row) {
                        cells.add(getReadedCell(cell, cellCount));
                        cellCount++;
                    }
                    rows.add(new it.workdocuments.project.model.excel.Row.RowBuilder()
                            .rowNumber(rowCount)
                            .cells(cells)
                            .build());
                    LOGGER.info("Line read correctly");
                    rowCount++;
                }
                LOGGER.info("Recovered {} rows ", rows.size());
                workSheet.setRows(rows);
                workSheets.add(workSheet);
            }
            document.setWorkSheets(workSheets);
            LOGGER.info("Recovered {} worksheets in the excel file", workSheets.size());
        }
        catch (Exception ex)
        {
            LOGGER.error("Error readDocument. Details: ", ex);
            throw ex;
        }
        LOGGER.info("End read excel file");
        return document;
    }
    @Override
    public String writeDocument(ExcelFile document) throws Exception {
        String pathFileName = "";
        try {
            LOGGER.info("Start write excel file");
            Workbook workbook = setWorkBook(document.getFileType(), FileAction.WRITE, null);
            pathFileName = Paths.get(document.getDirPath(), document.getFileName()  + "." + document.getExtension()).toString();
            LOGGER.info("Path excel: {}", pathFileName);

            for(ExcelWorkSheet workSheet : document.getWorkSheets()){

                LOGGER.info("Process the worksheet name: {}", workSheet.getSheetName().replace("/", "_"));
                Sheet sheet = workbook.createSheet(workSheet.getSheetName().replace("/", "_"));

                for(it.workdocuments.project.model.excel.Row row : workSheet.getRows()){
                    LOGGER.info("Insert line in position {}", row.getRowNumber());
                    Row rowToWrite = sheet.createRow(row.getRowNumber());
                    for(it.workdocuments.project.model.excel.Cell cell : row.getCells()){
                        insertCell(workbook, document.getFileType(), rowToWrite, cell, row.getRowNumber() == 0);
                    }
                    LOGGER.info("Line inserted correctly");
                    sheet.autoSizeColumn(row.getRowNumber());
                }
                LOGGER.info("Worksheet processed");
            }
            LOGGER.info("Worksheets processed, write the excel file at the path");
            writeFile(pathFileName, workbook);
            LOGGER.info("File written at the path: {}", pathFileName);
        }
        catch (Exception e){
            LOGGER.error("Error writeDocument. Details: ", e);
            throw e;
        }
        LOGGER.info("End write document");
        return pathFileName;
    }
    private Workbook setWorkBook(FileType fileType, FileAction fileAction, String fullPath) throws Exception {
        Workbook workbook = null;
        switch (fileType) {
            case XLS -> {
                if(fileAction == FileAction.WRITE){
                    workbook = new HSSFWorkbook();
                }
                else{
                    workbook = new HSSFWorkbook(new FileInputStream(fullPath));
                }
                LOGGER.info("Creating an xls file");
            }
            case XLSX -> {
                if(fileAction == FileAction.WRITE){
                    workbook = new XSSFWorkbook();
                }
                else{
                    workbook = new XSSFWorkbook(new FileInputStream(fullPath));
                }
                LOGGER.info("Creating an xlsx file");
            }
        }
        return workbook;
    }
    private void writeFile(String pathFileName, Workbook workbook) throws Exception {
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathFileName)) {
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            throw e;
        }
    }
    private Object getCellValueForType(Cell cell){
        Object value = null;
        try {
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue();
                    } else {
                        value = cell.getNumericCellValue();
                    }
                    break;
                case BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
            }
        }
        catch (Exception e){
            throw e;
        }
        return value;

    }
    private void insertCell(Workbook workbook, FileType fileType, Row row, it.workdocuments.project.model.excel.Cell cellToInsert, boolean isHeader) {
        try {
            LOGGER.info("Create cell in position {}", cellToInsert.getCellNumber());
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellToInsert.getCellNumber());
            if(isHeader && cellToInsert.getHeaderStyle() != null){
                LOGGER.info("Is header cell");
                cell.setCellStyle(setCellStyle(workbook, fileType, cellToInsert.getHeaderStyle()));
            }
            else if(!isHeader && cellToInsert.getValueStyle() != null){
                LOGGER.info("Is not header cell");
                cell.setCellStyle(setCellStyle(workbook, fileType, cellToInsert.getValueStyle()));
            }


            if(!Objects.isNull(cellToInsert.getFormula()) && !cellToInsert.getFormula().isEmpty()){
                cell.setCellFormula(cellToInsert.getFormula());
            }

            if(cellToInsert.getValue() instanceof String){
                cell.setCellValue((String) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof Boolean){
                cell.setCellValue((Boolean) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof Double){
                cell.setCellValue((Double) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof Integer){
                cell.setCellValue(Double.valueOf((Integer) cellToInsert.getValue()));
            }
            else if(cellToInsert.getValue() instanceof Date){
                cell.setCellValue((Date) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof Calendar){
                cell.setCellValue((Calendar) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof LocalDate){
                cell.setCellValue((LocalDate) cellToInsert.getValue());
            }
            else if(cellToInsert.getValue() instanceof LocalDateTime){
                cell.setCellValue((LocalDateTime) cellToInsert.getValue());
            }

            LOGGER.info("Cell value inserted: {}", cellToInsert.getValue());
        } catch(Exception e) {
            LOGGER.error("Error insertCell. Details: ", e);
            throw e;
        }
    }
    private it.workdocuments.project.model.excel.Cell getReadedCell(Cell cell, int cellCount) {
        LOGGER.debug("Read cell in position {}", cellCount);
        it.workdocuments.project.model.excel.Cell readedCell = new it.workdocuments.project.model.excel.Cell.CellBuilder()
                .cellNumber(cellCount).value(getCellValueForType(cell)).build();
        if(cell.getCellType() == CellType.FORMULA){
            readedCell.setFormula(cell.getCellFormula());
        }
        LOGGER.debug("Excel Cell read, the value is: {}", readedCell.getValue());
        return readedCell;
    }
    private CellStyle setCellStyle(Workbook workbook, FileType fileType, it.workdocuments.project.model.excel.style.CellStyle cellStyle){
        CellStyle cellStyleToAdd = null;
        try {
            LOGGER.info("Set the cell style");
            cellStyleToAdd = workbook.createCellStyle();
            if(cellStyle.getAlignment() != null){
                if(cellStyle.getAlignment().getHorizontalAlignment() != null){
                    cellStyleToAdd.setAlignment(cellStyle.getAlignment().getHorizontalAlignment());
                }
                if(cellStyle.getAlignment().getVerticalAlignment() != null){
                    cellStyleToAdd.setVerticalAlignment(cellStyle.getAlignment().getVerticalAlignment());
                }
            }
            if(cellStyle.getBorderTop() != null){
                cellStyleToAdd.setBorderTop(cellStyle.getBorderTop());
            }
            if(cellStyle.getBorderBottom() != null){
                cellStyleToAdd.setBorderBottom(cellStyle.getBorderBottom());
            }
            if(cellStyle.getBorderLeft() != null){
                cellStyleToAdd.setBorderLeft(cellStyle.getBorderLeft());
            }
            if(cellStyle.getBorderRight() != null){
                cellStyleToAdd.setBorderRight(cellStyle.getBorderRight());
            }
            if(cellStyle.getBackgroundColor() != null){
                cellStyleToAdd.setFillBackgroundColor(cellStyle.getBackgroundColor().getFillBackgroundColor().getIndex());
                cellStyleToAdd.setFillForegroundColor(cellStyle.getBackgroundColor().getFillForegroundColor().getIndex());
                cellStyleToAdd.setFillPattern(cellStyle.getBackgroundColor().getFillPatternType());
            }

            if(cellStyle.getFont() != null){
                Font font = workbook.createFont();
                font.setBold(cellStyle.getFont().isBold());
                if(!Objects.isNull(cellStyle.getFont().getFontName())){
                    font.setFontName(cellStyle.getFont().getFontName());
                }
                font.setItalic(cellStyle.getFont().isItalicFont());
                font.setStrikeout(cellStyle.getFont().isStrikeoutText());
                if(cellStyle.getFont().getFontHeight() != 0){
                    font.setFontHeight(cellStyle.getFont().getFontHeight());
                }
                if(cellStyle.getFont().getTypeOffset() != 0){
                    font.setTypeOffset(cellStyle.getFont().getTypeOffset());
                }
                if(cellStyle.getFont().getColor() != null){
                    font.setColor(cellStyle.getFont().getColor().getIndex());
                }
                if(cellStyle.getFont().getUnderline() != null){
                    font.setUnderline(cellStyle.getFont().getUnderline().getByteValue());
                }
                cellStyleToAdd.setFont(font);
            }
        }
        catch (Exception e){
            LOGGER.error("Error setCellStyle. Details: ", e);
            throw e;
        }
        LOGGER.info("End to set cell style");
        return cellStyleToAdd;
    }
}
