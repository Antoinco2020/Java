package it.workdocuments.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static String combine(String path, String... pathToAdds){
        return Paths.get(path, pathToAdds).toString();
    }
    public static boolean checkFileExists(String path) {
        boolean retValue = false;
        try {
            LOGGER.info("Verify the file exist, path: " + path);
            if(Files.exists(Paths.get(path))){
                LOGGER.info("The file exists");
                retValue = true;
            }
            else{
                LOGGER.info("The file not exists");
            }
        }
        catch (Exception e){
            LOGGER.error("Error checkFileExists. Details: ", e);
            throw e;
        }
        return retValue;
    }
    public static void deleteFile(String fullPath) throws IOException {
        LOGGER.info("Delete the file at the path {}", fullPath);
        Files.delete(Paths.get(fullPath));
    }
}
