package it.workdocuments.project.enums;

public enum FileType {
    XLS("xls"),
    XLSX("xlsx");

    public final String label;
    private FileType(String label){
        this.label = label;
    }
}
