package it.workdocuments.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExcelWorkSheet {
    private String sheetName;
    private List<Row> rows;

}
