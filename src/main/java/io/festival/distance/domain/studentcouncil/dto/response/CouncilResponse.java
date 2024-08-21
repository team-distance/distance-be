package io.festival.distance.domain.studentcouncil.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CouncilResponse(
    String school,
    SchoolLocation schoolLocation,
    List<ContentResponse> contentResponse

) {

    public static CouncilResponse toCouncilResponse(
        String school,
        SchoolLocation schoolLocation,
        List<ContentResponse> contentResponse
    ){
        return CouncilResponse.builder()
            .school(school)
            .schoolLocation(schoolLocation)
            .contentResponse(contentResponse)
            .build();
    }
}
