package com.pongift.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.plexus.util.StringUtils;

public class DateUtils {
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static Calendar getStrToDate(String strDate, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar getCalendar(int h, int m, int s) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, h);
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, s);
        return cal;
    }

    public static Calendar getCalendar(String day, String format) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(getDate(day, format));
        return cal;
    }

    public static Date getDate(String str, String dateFormat) {
        Date date = null;
        try {
            if (str == null || str.length() != dateFormat.length()) {
                return date;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(false);
            try {
                date = sdf.parse(str);
            } catch (ParseException pe) {
                date = new Date();
            }
        } catch (Exception e) {
            date = new Date();
        }

        return date;
    }


    public static Date getDate() {
        return new Date();
    }


    public static String getDate2String(Date date, String format) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        }

        return null;
    }


    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(1);
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(2) + 1;
    }

    /**
     * 특정 날짜의 요일  문자열 얻기
     *
     * @param sourceDate
     * @param sourceFormat
     * @return
     */
    public static String getWeekDay(String sourceDate, String sourceFormat) {
        String[] weekDayName = {"일", "월", "화", "수", "목", "금", "토"};
        if (StringUtils.isBlank(sourceDate)) {
            sourceDate = "";
        }
        Date date = DateUtils.getDate(sourceDate, sourceFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return weekDayName[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 유효한 날짜형식인가?
     *
     * @param date       - 날짜 format string
     * @param dateFormat - 원하는 날짜 format
     * @author 김정욱 2013. 11. 28.
     * @return                - 원하는 날짜 format 으로 parse 되면 true, exception false
     */
    public static boolean isValidDate(String date, String dateFormat) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(dateFormat);
            formatter.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getToday(String format) {
        return getDate2String(getDate(), format);
    }

    // wemakeprice 종료일 : 현재일기준 +5년 이하
    public static String getEndDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 4); //년 더하기
        cal.add(Calendar.MONTH, 11); //월 더하기
        cal.add(Calendar.DATE, 29); //일 더하기
        return dateFormat.format(cal.getTime());
    }

    public static String addDate(int y, int m, int d, int e) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        //Date date = format.parse(dt);
        //cal.setTime(date);
        cal.add(Calendar.YEAR, y); //년 더하기
        cal.add(Calendar.MONTH, m); //년 더하기
        cal.add(Calendar.DATE, d); //년 더하기
        cal.add(Calendar.MINUTE, e); //년 더하기
        return format.format(cal.getTime());
    }


    public static String getDate(Calendar calDate) {
        String strDate = "";
        if (calDate != null) {
            Date date = calDate.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                strDate = format1.format(date);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return strDate;
    }

    public static String getDate(Calendar calDate, String dateFormat) {
        String strDate = "";
        if (calDate != null) {
            Date date = calDate.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat(dateFormat);
            try {
                strDate = format1.format(date);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return strDate;
    }

    /**
     * 현재 날짜 기준 입력한 초 값 만큼 이후의 날짜 받기
     * @param seconds 시간 (초)
     * @return LocalDateTime
     */
    public static LocalDateTime getDateTimeAfterSeconds(long seconds) {
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
        return now.plusSeconds(seconds); // 입력한 초 값 만큼 이후의 날짜와 시간 계산
    }
}
