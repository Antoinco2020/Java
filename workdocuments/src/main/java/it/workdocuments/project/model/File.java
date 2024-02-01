package it.workdocuments.project.model;

import it.workdocuments.project.enums.FileType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class File {

    private String fileName;
    private FileType fileType;
    private String extension;
    private String dirPath;
    private byte[] content;

    protected void validateFile(File file){
        if(Objects.isNull(file.getFileName()) || file.getFileName().isEmpty()){
            throw new NullPointerException("The file name is mandatory");
        }
        else if(file.getFileName().contains(".")){
            throw new IllegalArgumentException("The file name must not contain the extension");
        }

        if(Objects.isNull(file.getExtension()) || file.getExtension().isEmpty()){
            throw new NullPointerException("The extension is mandatory");
        }
        else if(file.getExtension().contains(".")){
            throw new IllegalArgumentException("The extension must not contain the . character");
        }
        else if(!file.getExtension().equalsIgnoreCase(file.getFileType().label)){
            throw new IllegalArgumentException("The extension does not match the type");
        }

        if(Objects.isNull(file.getDirPath()) || file.getDirPath().isEmpty()){
            throw new NullPointerException("The directory path is mandatory");
        }
    }
}
