package com.ntu.java.weather;

import com.ntu.java.weather.dao.QueryDao;
import com.ntu.java.weather.dao.daoImpl.QueryDaoImpl;
import com.ntu.java.weather.model.Query;
import com.ntu.java.weather.model.ModelWeather;
import com.ntu.java.weather.service.CurrentWeatherService;
import com.ntu.java.weather.service.FiveDayWeatherService;
import com.ntu.java.weather.util.GetFromProperties;
import com.ntu.java.weather.util.MessageForButtons;
import com.ntu.java.weather.util.Parser;
import com.ntu.java.weather.util.WeatherWordTranslateFile;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private static final Logger logger = Logger.getLogger(Bot.class);
    private GetFromProperties getFromProperties = new GetFromProperties();
    private QueryDao queryDao = new QueryDaoImpl();
    static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        logger.info("Bot is initialized");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
            logger.info("Bot is registered");
        } catch (TelegramApiRequestException e) {
            logger.error("See the TelegramApiRequest", e);
        }
    }

    public void onUpdateReceived(Update update) {
        ModelWeather modelWeather = new ModelWeather();
        Message message = update.getMessage();
        MessageForButtons buttons = new MessageForButtons();
        logger.info("Massage is got");
        System.out.println(message);
        WeatherWordTranslateFile weaterWordTranslateFile = new WeatherWordTranslateFile();
        String weatherWordTranslater = null;
        if (message != null && message.hasText()) {
            logger.info("Massage is not null and has text");
            switch (message.getText()) {
                case "/start":
                    logger.info("Located in 'start' button");
                    break;
                case "/Як дивитися погоду":
                    logger.info("Located in 'howToSeeWeather' button");
                    sendMsg(message, buttons.readHowToSeeWeatherFile());
                    break;
                case "/Творець":
                    logger.info("Located in 'creator' button");
                    sendMsg(message, buttons.readCreatorFile());
                    break;
                case "/Переклад слів":
                    logger.info("Located in 'translateWeatherWord' button");
                    try {
                        weatherWordTranslater = weaterWordTranslateFile.readFile();
                        logger.info("File was found");
                    } catch (IOException e) {
                        logger.error("IOException, see the log", e);
                    }
                    sendMsg(message, weatherWordTranslater);
                    break;
                case "/Новини":
                    try {
                        sendMsg(message, Parser.getFromParsePage());
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "/Останні новини":
                    try {
                        List<String> parsers = new ArrayList<>();
                        String newsFromPage = Parser.getNewsFromPage(0);
                        String secondNewsFromPage = Parser.getNewsFromPage(1);
                        String thirdNewsFromPage = Parser.getNewsFromPage(2);
                        parsers.add(newsFromPage);
                        parsers.add(secondNewsFromPage);
                        parsers.add(thirdNewsFromPage);
                        sendMsg(message, String.valueOf(parsers));
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case "/Рокзлад Київської міської електрички":
                    sendMsg(message, "/Рокзлад Київської міської електрички");
                    break;
                case "ранок":
                    sendMsg(message, "ранок");
                    break;
                case "Дарниця вечір":
                    sendImageFromUrl(message);
                    break;
                case "Дарниця ранок":
                    sendImageFromUrl(message);
                    break;
                default:
                    try {
                        String queryFromTelegrmm = message.getText();
                        Query query = new Query();
                        query.setName(queryFromTelegrmm);
                        queryDao.addQuery(query);
                        if (queryFromTelegrmm.contains("fiveDay")) {
                            StringBuilder sb = new StringBuilder(queryFromTelegrmm);
                            sb = new StringBuilder(sb.substring(0, sb.length() - 8));
                            String convert = String.valueOf(sb);
                            sendMsg(message, FiveDayWeatherService.getFiveDayWeather(convert));
                        } else {
                            sendMsg(message, CurrentWeatherService.getWeather(message.getText(), modelWeather));
                        }
                    } catch (IOException e) {
                        logger.error("IOException, see the log", e);
                        String messageFromTelegramm = message.getText();
                        if (messageFromTelegramm.contains("Привіт") || messageFromTelegramm.contains("привіт")) {
                            sendMsg(message, "Вітаю Вас!");
                            break;
                        } else {
                            sendMsg(message, "Такого міста не існує, спробуйте ще раз!");
                        }
                    }
            }
        }
    }

    public void sendImageFromUrl(Message message) {

        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(message.getChatId().toString());
        if (message.getText().contains("Дарниця вечір")) {
            sendPhotoRequest.setPhoto("https://i.ibb.co/b3syKhY/Darnitsya-Vechir.png");
        } else if (message.getText().contains("Дарниця ранок")) {
            sendPhotoRequest.setPhoto("https://i.ibb.co/597jd5n/Darnitsya-Ranok.png");
        }

        try {
            sendPhoto(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, text);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("TelegramApiException", e);
        }
    }

    private void setButtons(SendMessage sendMessage, String text) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirsRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();


        keyboardFirsRow.add(new KeyboardButton("/Новини"));
        keyboardFirsRow.add(new KeyboardButton("/Творець"));
        keyboardFirsRow.add(new KeyboardButton("/Рокзлад Київської міської електрички"));
        keyboardSecondRow.add(new KeyboardButton("/Переклад слів"));
        keyboardSecondRow.add(new KeyboardButton("/Як дивитися погоду"));
        keyboardSecondRow.add(new KeyboardButton("/Останні новини"));

        keyboardRowList.add(keyboardFirsRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        if (text.contains("/Рокзлад Київської міської електрички")) {
            List<KeyboardRow> keyboardRowListRanokVechir = new ArrayList<>();
            keyboardFirsRow.clear();
            keyboardSecondRow.clear();
            keyboardFirsRow.add(new KeyboardButton("ранок"));
            keyboardFirsRow.add(new KeyboardButton("вечір"));
            keyboardRowListRanokVechir.add(keyboardFirsRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowListRanokVechir);
        }
        if (text.contains("ранок")) {
            List<KeyboardRow> keyboardRowListRanok = new ArrayList<>();
            keyboardFirsRow.clear();
            keyboardSecondRow.clear();
            keyboardFirsRow.add(new KeyboardButton("Дарниця ранок"));
            keyboardFirsRow.add(new KeyboardButton("Лівобережна ранок"));
            keyboardRowListRanok.add(keyboardFirsRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowListRanok);

        } else if (text.contains("вечір")) {

        }
    }

    public String getBotUsername() {
        return getFromProperties.getPropertiesValue("GET_BOT_USER_NAME");
    }

    public String getBotToken() {
        return getFromProperties.getPropertiesValue("GET_BOT_TOKEN");
    }
}
