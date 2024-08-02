package io.festival.distance.domain.councilgps.dto.response;

import io.festival.distance.domain.councilgps.entity.CouncilGps;
import lombok.Builder;

@Builder
public record CouncilGpsResponse(
    double councilLatitude,
    double councilLongitude,
    String location
) {
    public static CouncilGpsResponse toCouncilGpsResponse(CouncilGps councilGps){
        return CouncilGpsResponse.builder()
            .councilLatitude(councilGps.getCouncilLatitude())
            .councilLongitude(councilGps.getCouncilLongitude())
            .location(councilGps.getLocation())
            .build();
    }
}
