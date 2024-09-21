package com.daegurrr.daefree.utils;

import java.time.LocalDateTime;

public class Formatter {
    public static String dateToString(LocalDateTime dateTime) {
        return dateTime.getMonthValue()+"월 "+dateTime.getDayOfMonth()+"일";
    }
}
