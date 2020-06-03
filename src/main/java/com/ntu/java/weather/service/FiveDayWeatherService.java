package com.ntu.java.weather.service;

import com.ntu.java.weather.model.ModelWeather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FiveDayWeatherService {

    public static String getFiveDayWeather(String message) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + message + "&appid=ebb9e93de4d2322f1cbc8a5d4ce72e21");
        Scanner in = new Scanner((InputStream) url.getContent());

        List<ModelWeather> modelWeathers = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        List<Long> localDateTimes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LocalDateTime localDateTime = currentDate.plusDays(i + 1).atTime(15, 0);
            Long milisec = localDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
            localDateTimes.add(milisec);
        }

        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        System.out.println(result);

        JSONObject jsonObject = new JSONObject(result);
        JSONArray getArrayWeather = jsonObject.getJSONArray("list");
        modelWeathers.add(getForecastModelFromJson(getArrayWeather.getJSONObject(0)));

        for (int i = 1; i < getArrayWeather.length(); i++) {
            JSONObject objectFromArray = getArrayWeather.getJSONObject(i);
            Long dt = objectFromArray.getLong("dt");
            if (localDateTimes.contains(dt)) {
                modelWeathers.add(getForecastModelFromJson(getArrayWeather.getJSONObject(i)));
            } else {
                continue;
            }
        }

        StringBuilder returnRezult = new StringBuilder();
        for (ModelWeather modelWeather : modelWeathers) {
            String modelString = "Температура: " + modelWeather.getTemperature() + "C" + "\n" +
                    "Хмарність: " + modelWeather.getMain() + "\n" +
                    "Опис погоди: " + modelWeather.getDescription() + "\n" +
                    "Вологість повітря: " + modelWeather.getHumidity() + "%"  + "\n" +
                    "Дата: " + modelWeather.getDate() + "\n" +
                    getSmile() + "\n" + "\n";
            returnRezult.append(modelString);
        }
        return returnRezult.toString();
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

    private static ModelWeather getForecastModelFromJson(JSONObject jsonObject) {
        ModelWeather modelWeather = new ModelWeather();
        JSONObject mainFromJSON = jsonObject.getJSONObject("main");
        modelWeather.setTemperature(getTemperature(jsonObject));
        modelWeather.setHumidity(mainFromJSON.getDouble("humidity"));
        modelWeather.setDate(jsonObject.getString("dt_txt"));

        JSONArray getWeatherFromJson = jsonObject.getJSONArray("weather");
        for (int j = 0; j < getWeatherFromJson.length(); j++) {
            JSONObject weatherObjectFromArray = getWeatherFromJson.getJSONObject(j);
            modelWeather.setMain(String.valueOf(weatherObjectFromArray.get("main")));
            modelWeather.setDescription(String.valueOf(weatherObjectFromArray.get("description")));
            modelWeather.setIcon(String.valueOf(weatherObjectFromArray.get("icon")));
        }
        return modelWeather;
    }

    private static Double getTemperature(JSONObject jsonObject) {
        JSONObject mainFromJSON = jsonObject.getJSONObject("main");
        Double temperatureFromJSON = mainFromJSON.getDouble("temp");
        Double specificationValue = 273.15;
        Double v = temperatureFromJSON - specificationValue;
        Double convertToRound = (double) Math.round(v * 100) / 100;
        return convertToRound;
    }
}
