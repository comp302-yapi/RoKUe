package com.yapirokue.config;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {

    private static Configuration _configuration;
    private final Properties properties = new Properties();

    private Configuration() {
        try {
            String CONFIG_FILE_PATH = "src/main/resources/config/app_config.properties";
            FileInputStream propsInput = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(propsInput);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public static synchronized Configuration getInstance() {
        if (_configuration == null) _configuration = new Configuration();
        return _configuration;
    }

    public Object get(String key) {
        Object property = properties.get(key);

        if (property == null) {
            throw new RuntimeException("Failed accessing property " + key);
        }

        return property;
    }
}
