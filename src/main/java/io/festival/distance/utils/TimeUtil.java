package io.festival.distance.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class TimeUtil {
    public static long getSecondsUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return ChronoUnit.SECONDS.between(now, midnight);
    }
}
