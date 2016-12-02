package com.aca.pimp;
 

import android.content.Context;
import android.provider.Settings; 

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Marsel on 4/21/2015.
 */
public class UtilDate {
    public final static String DATE_PATTERN_DISPLAY_1 = "dd-MM-yyyy";
    public final static String DATE_PATTERN_DISPLAY_2 = "dd/MM";
    public final static String DATE_PATTERN_SERVER = "yyyy-MM-dd";

    public final static String TIME_PATTERN = "HH:mm";
    public final static String DATE_TIME_PATTERN_SERVER = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_PATTERN_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";


    public static void setAutoDateTime(Context context) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AUTO_TIME, 1);

    }

    public static int dayDiff(LocalDate fromDate, LocalDate toDate)
    {
        try {
            return Days.daysBetween(fromDate, toDate).getDays();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int monthDiff(LocalDate firstDate, LocalDate secondDate) {
        /**
         * secondate - firstdate
         * */
        return Months.monthsBetween(firstDate.withDayOfMonth(1), secondDate.withDayOfMonth(1)).getMonths();
    }

    public static int monthDiffInPeriode(LocalDate firstDate, LocalDate secondDate) {
        return Months.monthsBetween(firstDate, secondDate).getMonths();
    }

    public static int yearDiff(LocalDate firstDate, LocalDate secondDate) {
        return Years.yearsBetween(firstDate.withDayOfYear(1), secondDate.withDayOfYear(1)).getYears();
    }
    public static int yearDiffInPeriode(LocalDate firstDate, LocalDate secondDate) {
        return Years.yearsBetween(firstDate, secondDate).getYears();
    }


    public static Calendar addDay (LocalDate date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        calendar.add(Calendar.DAY_OF_YEAR, day);

        return calendar;
    }
    public static Calendar addMonth (LocalDate date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        calendar.add(Calendar.MONTH, month);

        return calendar;
    }

    public static Calendar addYear (LocalDate date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date.toDate());
        calendar.add(Calendar.YEAR, year);

        return calendar;
    }

    public static LocalDate getDate() {
        LocalDate localDate  = LocalDate.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
        return localDate;
    }

    public static LocalDate getDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    public static LocalTime getTime() {
        LocalTime localTime = LocalTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
        return localTime;
    }

    public static LocalTime getTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }


    public static LocalDateTime getDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
        return localDateTime;
    }


//    public static String mergeDate (int year, int month, int day, String pattern) {
//        try {
//            Calendar c = Calendar.getInstance();
//            c.set(year, month, day);
//
//            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//            String formattedDate = sdf.format(c.getTime());
//
//            return formattedDate;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static LocalDate  mergeDate (int year, int month, int day) {
        try {
        	LocalDate date = new LocalDate(year, month, day);
        	return date; 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    year = date.getYear();
    month = date.getMonthOfYear() - 1;
    day = date.getDayOfMonth();
*/

    public static LocalTime  mergeTime (int hour, int minute) {
        try {
            LocalTime localTime = getTime();
            localTime =  localTime.withHourOfDay(hour).withMinuteOfHour(minute);

            return localTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static LocalDate toDate (String tanggal){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_PATTERN_DISPLAY_1); // dd-MM-yyyy;

            formatter.parseLocalDate(tanggal);
            LocalDate date = formatter.parseLocalDate(tanggal);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static LocalDate toDate (String tanggal,String pattern){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern)
                    .withLocale(Locale.UK);

            LocalDate date = formatter.parseLocalDate(tanggal);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
        time.getHourOfDay()
        time.getMinuteOfHour()
     */

    public static LocalTime toTime (String waktu){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_PATTERN) // HH:mm
                    .withLocale(Locale.UK);

            LocalTime time = formatter.parseLocalTime(waktu);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalTime toTime (String waktu,String pattern){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern)
                    .withLocale(Locale.UK);

            LocalTime time = formatter.parseLocalTime(waktu);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime parseUTC(String tanggal) {
        DateTime dt = new DateTime(tanggal);
        LocalDateTime localDateTime = dt.toLocalDateTime();
        return localDateTime;
    }

    public static String format (String tanggal,String oldFormat,String newFormat) {
        DateTimeFormatter oldF = DateTimeFormat.forPattern(oldFormat);
        DateTimeFormatter newF = DateTimeFormat.forPattern(newFormat);


        LocalDate oldDate = null;
        String newDate = "";

        try {
            oldDate = oldF.parseLocalDate(tanggal);
            newDate = oldDate.toString(newF);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    public static String format (String tanggal, String newFormat) {
        DateTimeFormatter oldF = DateTimeFormat.forPattern(DATE_PATTERN_DISPLAY_1);
        DateTimeFormatter newF = DateTimeFormat.forPattern(newFormat);

        LocalDate oldDate = null;
        String newDate = "";

        try {
            oldDate = oldF.parseLocalDate(tanggal);
            newDate = oldDate.toString(newF);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    public static String formatServer (String tanggal) {
        DateTimeFormatter oldF = DateTimeFormat.forPattern(DATE_PATTERN_DISPLAY_1);
        DateTimeFormatter newF = DateTimeFormat.forPattern(DATE_PATTERN_SERVER);

        LocalDate oldDate = null;
        String newDate = "";

        try {
            oldDate = oldF.parseLocalDate(tanggal);
            newDate = oldDate.toString(newF);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

}
