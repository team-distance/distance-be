package io.festival.distance.authuniversity.dto.response;

import io.festival.distance.authuniversity.domain.University;
import lombok.Builder;

@Builder
public record SchoolResponse(String school) {
    public static SchoolResponse toSchoolResponse(University university){
        return SchoolResponse.builder()
            .school(university.getName())
            .build();
    }
}
