package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import java.util.Calendar;

public class Utility {
    public static Calendar[] createCalendar(int beginHour, int beginMinute, int endHour, int endMinute){
        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        beginCal.set(Calendar.HOUR_OF_DAY, beginHour);
        beginCal.set(Calendar.MINUTE, beginMinute);
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);
        return new Calendar[]{beginCal, endCal};
    }
}
