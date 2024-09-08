package io.festival.distance.domain.statistics.service.serviceimpl;

import static io.festival.distance.utils.DateUtil.getEndOfMonth;
import static io.festival.distance.utils.DateUtil.getEndOfWeek;
import static io.festival.distance.utils.DateUtil.getStartOfMonth;
import static io.festival.distance.utils.DateUtil.getStartOfWeek;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StatisticsTypeCheck(LocalDate startDate, LocalDate endDate) {

    public static StatisticsTypeCheck result(LocalDate date, String type){
        return switch (type) {
            case "daily" -> new StatisticsTypeCheck(date, date);
            case "weekly" -> new StatisticsTypeCheck(getStartOfWeek(date), getEndOfWeek(date));
            case "monthly" -> new StatisticsTypeCheck(getStartOfMonth(date), getEndOfMonth(date));
            default -> throw new DistanceException(ErrorCode.INVALID_TYPE);
        };
    }
}
