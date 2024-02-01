package it.workdocuments.project.utils;

import it.workdocuments.project.enums.FileAction;
import it.workdocuments.project.enums.FileType;
import it.workdocuments.project.model.Document;
import it.workdocuments.project.model.ExcelFile;
import it.workdocuments.project.model.ExcelWorkSheet;
import org.apache.poi.hpsf.SummaryInformation;
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
                List<it.workdocuments.project.model.Row> rows = new ArrayList<>();
                int rowCount = 0;
                for(Row row : sheet){
                    LOGGER.info("Read line in position {}", rowCount);
                    List<it.workdocuments.project.model.Cell> cells = new ArrayList<>();
                    int cellCount = 0;
                    for (Cell cell : row) {
                        cells.add(getReadedCell(cell, cellCount));
                        cellCount++;
                    }
                    rows.add(new it.workdocuments.project.model.Row.RowBuilder()
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

                for(it.workdocuments.project.model.Row row : workSheet.getRows()){
                    LOGGER.info("Insert line in position {}", row.getRowNumber());
                    Row rowToWrite = sheet.createRow(row.getRowNumber());
                    for(it.workdocuments.project.model.Cell cell : row.getCells()){
                        insertCell(rowToWrite, cell);
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
    private void insertCell(Row row, it.workdocuments.project.model.Cell cellToInsert) {
        try {
            LOGGER.info("Create cell in position {}", cellToInsert.getCellNumber());
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellToInsert.getCellNumber());
            if(cellToInsert.getValueStyle() != null){
                cell.setCellStyle(cellToInsert.getValueStyle());
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
    private it.workdocuments.project.model.Cell getReadedCell(Cell cell, int cellCount) {
        LOGGER.debug("Read cell in position {}", cellCount);
        it.workdocuments.project.model.Cell readedCell = new it.workdocuments.project.model.Cell.CellBuilder()
                .cellNumber(cellCount).value(getCellValueForType(cell)).build();
        if(cell.getCellType() == CellType.FORMULA){
            readedCell.setFormula(cell.getCellFormula());
        }
        LOGGER.debug("Excel Cell read, the value is: {}", readedCell.getValue());
        return readedCell;
    }
}
