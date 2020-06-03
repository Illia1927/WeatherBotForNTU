package com.ntu.java.weather.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetFromProperties {
    public String getPropertiesValue(String propetyName) {
        String propertyValue = "";
        Properties properties = new Properties();
        try (InputStream fromResources = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            properties.load(fromResources);
            propertyValue = properties.getProperty(propetyName);
        } catch (IOException e) {

        }
        return propertyValue;
    }
}
