package io.festival.distance.domain.studentcouncil.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ContentDetailResponse(
    String title,
    LocalDateTime createDt
) {

}
