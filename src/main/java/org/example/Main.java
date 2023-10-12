package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws Exception {
        //Получаем объект Document, представление HTML страницы.
        Document page = Parser.getPage();
        //Получаем объект Element, выделяем нужную таблицу, в которой представлена информация по погоде.
        Element tableWeather = page.select("table[class=wt]").first();
        assert tableWeather != null;
        //Получаем из общей таблицы даты
        Elements names = tableWeather.select("tr[class=wth]");
        //Получаем из общей таблицы значения погоды
        Elements values = tableWeather.select("tr[valign=top]");
        int index = 0;

        //Вывод погоды на экран на ближайшие 5 дней
        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text(); //Получаем текстовое значение заголовка
            String date = Parser.getDateFromString(dateString);  //Переводим заданный формат даты
            System.out.printf("%-10s %-70s %-15s %-10s %-15s %-15s",date, "Явления","Температура","Давление",
                    "Влажность","Ветер"); //Печатаем отформатированную шапку таблицы
            System.out.println();
            index += Parser.printFourValues(values, index);
        }
    }
}