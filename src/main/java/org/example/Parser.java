package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    static Document getPage() throws IOException {
        //Получаем объект Document, представление HTML страницы.
        String url = "https://www.pogoda.msk.ru";
        return Jsoup.parse(new URL(url), 3000);
    }

    //Чтобы дата выводилась определенного формата без лишней информации, создаем паттерн.
    private static final Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
    static String getDateFromString(String stringDate) throws Exception {
        //Получаем дату необходимого формата из строки.
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()){
            return matcher.group();
        }
        throw new Exception("Can't extract date from string");
    }

    static int printFourValues(Elements values, int index) {
        //Выводим на печать информацию по погоде за день (Утро, день, вечер, ночь).
        int iterationCount = 4;

        if (index==0) {
            Element valueLn = values.get(0);
            /*Т.к. представление погоды меняется на сайте в зависимости от времени, проверяем воспадение первого
            элемента, для корректного вывода на оставшиеся сутки. */
            if (valueLn.text().contains("День")){
                iterationCount = 3;
            } else if (valueLn.text().contains("Вечер")){
                iterationCount = 2;
            } else if (valueLn.text().contains("Ночь")){
                iterationCount = 1;
            }
        }

        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            int j=0;
            //Получаем нужный тег и выводим на экран с добавлением форматирования, чтобы информация была читабельной.
            for (Element td : valueLine.select("td")) {
                switch (j) {
                    case (0):
                    case (3):
                    case (5):
                    case (4):
                        System.out.printf("%-10s", td.text());
                        j++;
                        break;
                    case (1):
                        System.out.printf("%-70s", td.text());
                        j++;
                        break;
                    case (2):
                        System.out.printf("%-20s", td.text());
                        j++;
                        break;
                }
            }
            System.out.println();
        }
        return iterationCount;
    }
}
