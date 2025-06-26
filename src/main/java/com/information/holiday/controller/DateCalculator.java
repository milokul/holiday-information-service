package com.information.holiday.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculator {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String format(Date date) {
        return sdf.format(date);
    }

    public String addOneYear(String date) {

        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = sdf.parse(date);
        } catch (RuntimeException | ParseException e) {
            throw new IllegalArgumentException(e);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(convertedCurrentDate);
        c.add(Calendar.YEAR, 1);
        return sdf.format(c.getTime());
    }

    public String addOneDay(String date) {
        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = sdf.parse(date);
        } catch (RuntimeException | ParseException e) {
            throw new IllegalArgumentException(e);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(convertedCurrentDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(c.getTime());
    }

}
