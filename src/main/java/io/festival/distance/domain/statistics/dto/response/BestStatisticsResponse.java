package io.festival.distance.domain.statistics.dto.response;

import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.statistics.repository.BestStatisticsProjection;
import lombok.Builder;

@Builder
public record BestStatisticsResponse(
    Long councilId,
    String title,
    int count
) {
    public static BestStatisticsResponse toBestStatisticsResponse(
        BestStatisticsProjection bestStatisticsProjection){
        return BestStatisticsResponse.builder()
            .councilId(bestStatisticsProjection.getCouncilId())
            .count(bestStatisticsProjection.getCount())
            .title(bestStatisticsProjection.getTitle())
            .build();
    }
}
