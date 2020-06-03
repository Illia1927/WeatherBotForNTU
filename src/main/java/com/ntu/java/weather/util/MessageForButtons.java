package com.ntu.java.weather.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageForButtons {
    private static final Logger logger = Logger.getLogger(MessageForButtons.class);
    private static GetFromProperties getFromProperties = new GetFromProperties();
    private File buttonFile = null;
    private String separator = File.separator;
    private String urlToFile = "D:" + separator + "Programming" + separator +
            "WeatherBotForNTU-weatherBot" + separator;
    private String url = getUrlToHtswFile();

    public String readHowToSeeWeatherFile() {
        urlToFile = urlToFile + "howToSeeWeather.txt";
        buttonFile = new File(urlToFile);
        logger.info("url is ok");
        String readFile = null;
        try {
            readFile = FileUtils.readFileToString(buttonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFile;
    }

    public String readCreatorFile() {
        urlToFile = urlToFile + "creator.txt";
        buttonFile = new File(urlToFile);
        logger.info("url is ok");
        String readFile = null;
        try {
            readFile = FileUtils.readFileToString(buttonFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFile;
    }

    public static String getUrlToHtswFile() {
        return getFromProperties.getPropertiesValue("URL_TO_HTSW_FILE");
    }

}
