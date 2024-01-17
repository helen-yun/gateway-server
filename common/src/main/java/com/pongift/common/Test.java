package com.pongift.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by kimg on 2019-07-17.
 */
public class Test {
    public static void main(String[] args){
        LocalDateTime now = LocalDateTime.now();
        String now2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        System.out.println(now2);
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());
    }
}
