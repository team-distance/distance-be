package io.festival.distance.domain.studentcouncil.dto.response;

import lombok.Builder;

@Builder
public record SchoolLocation(
    double latitude,
    double longitude
) {
    public static SchoolLocation toSchoolLocation(double latitude, double longitude){
        return SchoolLocation.builder()
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
