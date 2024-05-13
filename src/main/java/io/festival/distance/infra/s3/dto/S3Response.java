package io.festival.distance.infra.s3.dto;

import lombok.Builder;

@Builder
public record S3Response(
    String imageUrl,
    String fileName
) {

}
