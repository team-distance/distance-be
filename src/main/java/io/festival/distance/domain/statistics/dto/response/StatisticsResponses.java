package io.festival.distance.domain.statistics.dto.response;

import java.util.List;
import lombok.Builder;

/**
 * 일급컬렉션
 * @param responsesList
 */
@Builder
public record StatisticsResponses(
    List<StatisticsResponse> responsesList
) {
    public void addResponse(StatisticsResponse response) {
        responsesList.add(response);  // responsesList에 바로 추가
    }
}
