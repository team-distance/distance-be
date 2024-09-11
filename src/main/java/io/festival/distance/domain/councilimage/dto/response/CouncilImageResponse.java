package io.festival.distance.domain.councilimage.dto.response;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import lombok.Builder;

@Builder
public record CouncilImageResponse(String imageUrl, int priority) {
    public static CouncilImageResponse toCouncilImageResponse(CouncilImage councilImage){
        return CouncilImageResponse.builder()
            .imageUrl(councilImage.getImageUrl())
            .priority(councilImage.getPriority())
            .build();
    }
}
