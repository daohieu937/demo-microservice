package com.example.userservice.media.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static Date getDateAfter(long seconds) {
        long expTimeMillis = new Date().getTime();
        expTimeMillis += seconds * 1000;
        return new Date(expTimeMillis);
    }

    public static Integer calculateAge(Date startDateInput, Date endDateInput) {
        LocalDate startDate = startDateInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDateInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(startDate, endDate).getYears();
    }

    public static Integer calculateAge(LocalDate startDateInput, LocalDate endDateInput) {
        return Period.between(startDateInput, endDateInput).getYears();
    }
}
