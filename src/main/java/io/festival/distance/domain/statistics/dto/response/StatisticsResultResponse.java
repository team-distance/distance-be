package io.festival.distance.domain.statistics.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StatisticsResultResponse(
    String title,
    LocalDateTime createDt,
    StatisticsResponses statisticsResponses
) {

    public static StatisticsResultResponse toResult(
        String title,
        LocalDateTime createDt,
        StatisticsResponses statisticsResponses
    ) {
        return StatisticsResultResponse.builder()
            .title(title)
            .createDt(createDt)
            .statisticsResponses(statisticsResponses)
            .build();
    }
}