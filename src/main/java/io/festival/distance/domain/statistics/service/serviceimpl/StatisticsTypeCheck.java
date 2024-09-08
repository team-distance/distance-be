package io.festival.distance.domain.statistics.service.serviceimpl;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import io.festival.distance.utils.DateUtil;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StatisticsTypeCheck(LocalDate startDate, LocalDate endDate) {

    public static StatisticsTypeCheck resultByCount(LocalDate date, String type, int countWeek){
        if (countWeek <= 0) {
            countWeek = 0;
        }

        return switch (type) {
            case "daily" -> new StatisticsTypeCheck(date.minusDays(countWeek), date);
            case "weekly" -> new StatisticsTypeCheck(
                DateUtil.getStartOfWeek(date.minusWeeks(countWeek)),
                DateUtil.getEndOfWeek(date)
            );
            case "monthly" -> new StatisticsTypeCheck(
                DateUtil.getStartOfMonth(date.minusMonths(countWeek)),
                DateUtil.getEndOfMonth(date)
            );
            default -> throw new DistanceException(ErrorCode.INVALID_TYPE);
        };
    }

    public static StatisticsTypeCheck result(LocalDate date, String type){

        return switch (type) {
            case "daily" -> new StatisticsTypeCheck(date, date);
            case "weekly" -> new StatisticsTypeCheck(
                DateUtil.getStartOfWeek(date),
                DateUtil.getEndOfWeek(date)
            );
            case "monthly" -> new StatisticsTypeCheck(
                DateUtil.getStartOfMonth(date),
                DateUtil.getEndOfMonth(date)
            );
            default -> throw new DistanceException(ErrorCode.INVALID_TYPE);
        };
    }
}
