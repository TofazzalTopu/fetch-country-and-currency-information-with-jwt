package com.info.country.utility;

import java.util.Calendar;
import java.util.Date;

public class TimeUtility {

    public static Date getPreviousTimeInMinutes(int minute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -minute);
        Date threeMinutesBack = cal.getTime();
        return threeMinutesBack;
    }
}
