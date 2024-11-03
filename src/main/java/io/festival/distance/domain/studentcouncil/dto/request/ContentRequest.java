package io.festival.distance.domain.studentcouncil.dto.request;

import io.festival.distance.domain.councilgps.dto.request.CouncilGpsRequest;
import io.festival.distance.domain.image.dto.request.FileListRequest;
import java.time.LocalDate;
import java.util.List;

public record ContentRequest(
    String title,
    String content,
    LocalDate startDt,
    LocalDate endDt,
    List<CouncilGpsRequest> councilGpsRequestList,
    FileListRequest fileListRequest,
    List<Integer> priority
) {

}
