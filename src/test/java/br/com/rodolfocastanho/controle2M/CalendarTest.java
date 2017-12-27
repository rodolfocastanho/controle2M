package br.com.rodolfocastanho.controle2M;

import java.text.DateFormatSymbols;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarTest {

    public static void main(String[] args){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());


        System.out.println(date);
        System.out.println(setHours(date, 0, 0, 0));
        System.out.println(somaDia(date));
        System.out.println();

        date = somaDia(date);

        System.out.println(date);
        System.out.println(setHours(date, 23,59,59));
        System.out.println(somaDia(date));

    }


    private static Date somaDia(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private static Date setHours(Date data, int h, int m, int s){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH ),
                h,
                m,
                s);
        return calendar.getTime();
    }

}
