package org.example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws Exception {
        Document page = Parser.getPage();
        //css query language
        Element tableWeather = page.select("table[class=wt]").first();
        assert tableWeather != null;
        Elements names = tableWeather.select("tr[class=wth]");
        Elements values = tableWeather.select("tr[valign=top]");
        int index = 1;
        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = Parser.getDateFromString(dateString);
            System.out.printf("%-10s %-70s %-15s %-10s %-15s %-15s",date, "Явления","Температура","Давление",
                    "Влажность","Ветер");
            System.out.println();

            index += Parser.printFourValues(values, index);
        }
    }
}