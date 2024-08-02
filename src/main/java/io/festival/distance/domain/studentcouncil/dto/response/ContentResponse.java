package io.festival.distance.domain.studentcouncil.dto.response;

import io.festival.distance.domain.councilgps.dto.response.CouncilGpsResponse;
import io.festival.distance.domain.councilimage.dto.response.CouncilImageResponse;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record ContentResponse(
    Long councilId,
    String title,
    String content,
    LocalDate startDt,
    LocalDate endDt,
    List<CouncilGpsResponse> councilGpsResponses,
    List<CouncilImageResponse> councilImageResponses
) {

    public static ContentResponse toContentResponse(
        StudentCouncil studentCouncil,
        List<CouncilGpsResponse> councilGpsResponses,
        List<CouncilImageResponse> councilImageResponses
    ) {
        return ContentResponse.builder()
            .councilId(studentCouncil.getCouncilId())
            .title(studentCouncil.getTitle())
            .content(studentCouncil.getContent())
            .startDt(studentCouncil.getStartDt())
            .endDt(studentCouncil.getEndDt())
            .councilGpsResponses(councilGpsResponses)
            .councilImageResponses(councilImageResponses)
            .build();
    }
}
