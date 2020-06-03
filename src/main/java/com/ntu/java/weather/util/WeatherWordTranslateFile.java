package com.ntu.java.weather.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WeatherWordTranslateFile {
    private static final Logger logger = Logger.getLogger(WeatherWordTranslateFile.class);

    public String readFile() throws IOException {
        String separator = File.separator;
        String urlToFile = "D:" + separator + "Programming" + separator +
                "WeatherBotForNTU-weatherBot" + separator + "translateFile.txt";
        File translatefile = new File(urlToFile);
        logger.info("url is ok");
        return FileUtils.readFileToString(translatefile, StandardCharsets.UTF_8);
    }
}
