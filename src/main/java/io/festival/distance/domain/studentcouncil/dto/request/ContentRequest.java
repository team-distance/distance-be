package io.festival.distance.domain.studentcouncil.dto.request;

import io.festival.distance.domain.councilgps.dto.request.CouncilGpsRequest;
import java.time.LocalDate;
import java.util.List;

public record ContentRequest(
    String title,
    String content,
    LocalDate startDt,
    LocalDate endDt,
    List<CouncilGpsRequest> councilGpsRequestList
) {

}
