package com.project.config;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class Config {

    private Properties properties;
    private static Config config;
    private static final String configPath = "src/main/resources/application.properties";

    private Config(){
        loadProperties();
    }

    public static Config getConfig(){
        if (config == null)
                config = new Config();
        return config;
    }

    private void loadProperties(){
        try (InputStream inputStream = new FileInputStream(configPath)){
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
