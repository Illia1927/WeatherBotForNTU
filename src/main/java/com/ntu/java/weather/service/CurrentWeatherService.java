package com.ntu.java.weather.service;

import com.ntu.java.weather.model.ModelWeather;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CurrentWeatherService {
    private static final Logger logger = Logger.getLogger(CurrentWeatherService.class);

    public static String getWeather(String message, ModelWeather modelWeather) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=ebb9e93de4d2322f1cbc8a5d4ce72e21");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        logger.info("JSON reques is : " + result);

        JSONObject objectFromJson = new JSONObject(result);
        modelWeather.setName(objectFromJson.getString("name"));

        JSONObject mainFromJSON = objectFromJson.getJSONObject("main");
        modelWeather.setTemperature(mainFromJSON.getDouble("temp"));
        modelWeather.setHumidity(mainFromJSON.getDouble("humidity"));

        JSONObject sysFromJSON = objectFromJson.getJSONObject("sys");
        modelWeather.setCountry(sysFromJSON.getString("country"));

        modelWeather.setSunrise(String.valueOf(sysFromJSON.getLong("sunrise")));
        Long sunrise = Long.valueOf(modelWeather.getSunrise());
        Date sunriseDate = new Date(sunrise * 1000);
        SimpleDateFormat spdSunrise = new SimpleDateFormat("HH:mm:ss");
        String formatSunrise = spdSunrise.format(sunriseDate);

        modelWeather.setSunset(String.valueOf(sysFromJSON.getLong("sunset")));
        Long sunset = Long.valueOf(modelWeather.getSunset());
        Date sunsetDate = new Date(sunset * 1000);
        SimpleDateFormat spdSunset = new SimpleDateFormat("HH:mm:ss");
        String formatSunset = spdSunset.format(sunsetDate);

        JSONArray getArrayWeather = objectFromJson.getJSONArray("weather");
        for (int i = 0; i < getArrayWeather.length(); i++) {
            JSONObject objectFromArray = getArrayWeather.getJSONObject(i);
            modelWeather.setIcon((String) objectFromArray.get("icon"));
            modelWeather.setMain((String) objectFromArray.get("main"));
            modelWeather.setDescription((String) objectFromArray.get("description"));
        }

        String returnRezult = "Місто: " + modelWeather.getName() + "\n" +
                "Температура: " + modelWeather.getTemperature() + "C" + "\n" +
                "Вологість повітря: " + modelWeather.getHumidity() + "%" + "\n" +
                "Хмарність: " + modelWeather.getMain() + "\n" +
                "Опис погоди: " + modelWeather.getDescription() + "\n" +
                "Країна: " + modelWeather.getCountry() + "\n" +
                "Схід сонця: " + formatSunrise + "\n" +
                "Захід слонця: " + formatSunset + "\n" +
                getSmile() + "\n" +
                "http://openweathermap.org/img/w/" + modelWeather.getIcon() + ".png" + "\n";

        return returnRezult;
    }
    private static String getSmile() {
        String smile = " ☁ " +
                " \uD83C\uDF1E " +
                " \uD83C\uDF27 " +
                " \uD83C\uDF02 " +
                " \uD83D\uDCA8 " +
                " \uD83C\uDF0A " +
                " ⚡ ";
        return smile;
    }
}
