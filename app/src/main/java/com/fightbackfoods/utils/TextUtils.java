package com.fightbackfoods.utils;

import com.fightbackfoods.model.Nutrients;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TextUtils {
    public static final int MALE = 1;
    public static final int FEMALE = 0;

    /*
    @    "yyyy-MM-dd";
     */
    public static final String SDF_1 = "yyyy-MM-dd";
    public static final String SDF_2 = "yyyy-MM-dd HH:mm:ss";


    public static String getTimeDuration(String startTime, String endTime) {

        return startTime + " - " + endTime;
    }

    public static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {

        }
        return 0;
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatDate(String date, String formatFrom, String formatTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatFrom);
        return formatDate(formatTo, sdf.parse(date));
    }

    public static String formatTime(String time, String formatFrom, String formatTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatFrom);
        return formatDate(formatTo, sdf.parse(time));
    }

    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDateFormatToday(){
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_1);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
       /* Calendar c = Calendar.getInstance();
        sdf.setTimeZone(c.getTimeZone());*/
        return sdf.format(new Date());
    }
}
