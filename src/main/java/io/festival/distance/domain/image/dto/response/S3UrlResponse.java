package io.festival.distance.domain.image.dto.response;

import lombok.Builder;

@Builder
public record S3UrlResponse(String s3Url) {
    public static S3UrlResponse toS3UrlResponse(String url){
        return S3UrlResponse.builder()
            .s3Url(url)
            .build();
    }
}
