package it.restapp.project.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcePropertiesUtil {

    private final Properties properties;

    @JsonCreator
    private ResourcePropertiesUtil(@JsonProperty("propertiesFile") String propertiesFile){
        try {
            // Load the endpoint.properties file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);

            // Create a new Properties object and load the contents of the file into it
            properties = new Properties();
            properties.load(inputStream);

            // Close the input stream
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties file", e);
        }
    }

    public static ResourcePropertiesUtil getInstance(String propertiesFile) {
        return new ResourcePropertiesUtil(propertiesFile);
    }
    public <T> T getPropertyAs(String name, Class<T> type){
        Object out = properties.get(name);
        return type.cast(out);
    }
    public <T> T getPropertyAs(String name, Class<T> type, T defaultValue){
        T out = getPropertyAs(name, type);
        if(out == null){
            return defaultValue;
        } else {
            return out;
        }
    }
    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
