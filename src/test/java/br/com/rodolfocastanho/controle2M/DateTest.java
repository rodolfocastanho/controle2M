package br.com.rodolfocastanho.controle2M;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTest {

    public static void main(String[] args){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());

        System.out.println(diaSemana(date));

        Date lastDayOfMonth = resolveUltimoDiaMes(date);
        System.out.println(lastDayOfMonth);
        int lastWeekOfMonth = resolveQtdSemanas(lastDayOfMonth);
        System.out.println(lastWeekOfMonth);


        int count = 1;
        Date countDateInicio = date;
        Date countDateFim = date;
        while (count <= lastWeekOfMonth){
            Date prim = resolvePrimeiroUltimo(countDateInicio, true);
            Date ult = resolvePrimeiroUltimo(countDateInicio, false);

            if(count == lastWeekOfMonth) ult = lastDayOfMonth;

            System.out.println("Semana " + count);
            System.out.println(prim+" - "+ult);
            System.out.println(qtdDiasSemana(countDateInicio, ult));

            count++;
            countDateInicio = somaDia(ult);

        }



    }

    private static Date resolvePrimeiroUltimo(Date data, boolean isPrimeiro)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        if(isPrimeiro) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }

        return calendar.getTime();
    }

    private static Date resolveUltimoDiaMes(Date data)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    private static int resolveQtdSemanas(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    private static Date somaDia(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private static long qtdDiasSemana(Date d1,Date d2){
        Calendar d1Calendar = Calendar.getInstance();
        Calendar d2Calendar = Calendar.getInstance();
        d1Calendar.setTime(d1);
        d2Calendar.setTime(d2);
        long dias = ChronoUnit.DAYS.between(d1Calendar.getTime().toInstant(),d2Calendar.getTime().toInstant());
        return dias+1;
    }

    private static String diaSemana(Date data){
        String[] getDiaSemanaExtenso = new DateFormatSymbols(new Locale("pt", "BR")).getWeekdays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return getDiaSemanaExtenso[calendar.get(Calendar.DAY_OF_WEEK)];
    }

}
