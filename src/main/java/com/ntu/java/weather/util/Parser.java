package com.ntu.java.weather.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {
    private static GetFromProperties getFromProperties = new GetFromProperties();
    private static Document getPage() throws IOException {
        String url = getUrlForNews();
        Document page = Jsoup.parse(new URL(url), 100000);
        return page;
    }

    public static String getFromParsePage() throws IOException {
        Document page = getPage();
        Element getFirstAttribute = page.select(getFirstDivAttForLink()).first();
        Elements valuesFromAttribute = getFirstAttribute.select(getValuesFromFirstAtt());
        Element hrefFormNews = valuesFromAttribute.first();

        StringBuilder getInfoFromHref = new StringBuilder(String.valueOf(hrefFormNews));
        getInfoFromHref = new StringBuilder(getInfoFromHref.substring(0, getInfoFromHref.length() - 20));
        String convert = String.valueOf(getInfoFromHref);
        StringBuffer operation = new StringBuffer(convert);
        StringBuffer delete = operation.delete(0, 9);
        String rezult = "Актуальні " + valuesFromAttribute.text() + "\uD83C\uDDFA\uD83C\uDDE6" + ":" + "\n" + delete;
        return rezult;
    }

    public static String getNewsFromPage(int id) throws IOException {
        Document page = getPage();
        Element getFirstAttribute = page.select(getFirstDivAttForLastThreeNews()).first();
        Elements values = getFirstAttribute.select(getValuesFromFirstAttForLastThreeNews());
        List<Element> collect = values.stream().limit(3).collect(Collectors.toList());
        Pattern p = Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");

        Element element = collect.get(id);
        Matcher m = p.matcher(element.toString());
        if (m.find()) {
            String getLinkFromPage = m.group();
            String rezult = element.text() + " \uD83D\uDCF0 \uD83C\uDDFA\uD83C\uDDE6" + "\n" + "Лінка: " + "↘" + "\n" + getLinkFromPage + "\n" + "\n";
            return rezult;
        }
        return "Спробуйте ще раз";
    }

    private static String getUrlForNews() {
        return getFromProperties.getPropertiesValue("URL_FOR_NEWS");
    }
    private static String getFirstDivAttForLink() {
        return getFromProperties.getPropertiesValue("FIRST_DIV_ATT_FOR_LINK");
    }
    private static String getValuesFromFirstAtt() {
        return getFromProperties.getPropertiesValue("VALUES_FROM_FIRST_ATT");
    }
    private static String getFirstDivAttForLastThreeNews() {
        return getFromProperties.getPropertiesValue("FIRST_DIV_ATT_FOR_LAST_THREE_NEWS");
    }
    private static String getValuesFromFirstAttForLastThreeNews() {
        return getFromProperties.getPropertiesValue("VALUES_FROM_FIRST_ATT_FOR_LAST_THREE_NEWS");
    }
}
