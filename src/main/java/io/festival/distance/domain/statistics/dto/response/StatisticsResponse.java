package io.festival.distance.domain.statistics.dto.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StatisticsResponse(
    LocalDate startDate,
    int count
) {

    public static StatisticsResponse toStatisticsResponse(LocalDate startDate, int count) {
        return StatisticsResponse.builder()
            .startDate(startDate)
            .count(count)
            .build();
    }

    public static StatisticsResponse fromCount(LocalDate startDate, int count){
        return StatisticsResponse.builder()
            .startDate(startDate)
            .count(count)
            .build();
    }
}
