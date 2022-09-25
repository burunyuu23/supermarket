package ru.vsu.edu.shlyikov_d_g;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePrinter {

    private final SimpleDateFormat dateFormatGame = new SimpleDateFormat("d MMMM yyyy");
    private final Calendar calendar = new GregorianCalendar();

    public DatePrinter() {
    }

    public void add(Integer x) {
        calendar.add(Calendar.DATE, x);
    }

    public void printGame(){
        System.out.println(dateFormatGame.format(calendar.getTime()));
    }

    public void printStart(){
        System.out.println("Рабочий день начинается! Время: 8:00 " + dateFormatGame.format(calendar.getTime()));
    }

    public void printEnd(){
        System.out.println("Рабочий день окончен! Время: 22:00 " + dateFormatGame.format(calendar.getTime()));
    }
}
