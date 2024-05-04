package io.festival.distance.domain.admin.adminfestival.foodtruck.dto;

import lombok.Builder;

@Builder
public record S3Response(
    String imageUrl,
    String fileName
) {

}
