package it.workdocuments.project.model.excel;

import it.workdocuments.project.enums.FileType;
import it.workdocuments.project.model.File;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class ExcelFile extends File {

    private List<ExcelWorkSheet> workSheets;

    public ExcelFile(ExcelBuilder builder){
        this.setFileName(builder.fileName);
        this.setFileType(builder.fileType);
        this.setExtension(builder.extension);
        this.setDirPath(builder.dirPath);
        this.setContent(builder.content);
        this.setWorkSheets(builder.workSheets);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    public static class ExcelBuilder{
        private String fileName;
        private FileType fileType;
        private String extension;
        private String dirPath;
        private byte[] content;
        private List<ExcelWorkSheet> workSheets;

        public ExcelBuilder fileName(String fileName){
            this.fileName = fileName;
            return this;
        }
        public ExcelBuilder fileType(FileType fileType){
            this.fileType = fileType;
            return this;
        }
        public ExcelBuilder extension(String extension){
            this.extension = extension;
            return this;
        }
        public ExcelBuilder dirPath(String dirPath){
            this.dirPath = dirPath;
            return this;
        }
        public ExcelBuilder content(byte[] content){
            this.content = content;
            return this;
        }
        public ExcelBuilder workSheets(List<ExcelWorkSheet> workSheets){
            this.workSheets = workSheets;
            return this;
        }

        public ExcelFile build() {
            ExcelFile excelFile =  new ExcelFile(this);
            excelFile.validateFile(excelFile);
            return excelFile;
        }
    }
}
