package io.festival.distance.domain.councilimage.dto.response;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import lombok.Builder;

@Builder
public record CouncilImageResponse(String imageUrl) {
    public static CouncilImageResponse toCouncilImageResponse(CouncilImage councilImage){
        return CouncilImageResponse.builder()
            .imageUrl(councilImage.getImageUrl())
            .build();
    }
}
