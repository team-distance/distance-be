package io.festival.distance.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {
    public static LocalDate getStartOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getEndOfWeek(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    public static LocalDate getStartOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getEndOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static Date setExpirationTime(long currentTime){
        return new Date(currentTime + 1000 * 60 * 60);
    }
}
