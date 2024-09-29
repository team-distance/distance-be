package io.festival.distance.domain.image.dto.response;

import lombok.Builder;

@Builder
public record S3UrlResponse(String s3Url, String fileName) {
    public static S3UrlResponse toS3UrlResponse(String url,String fileName){
        return S3UrlResponse.builder()
            .fileName(fileName)
            .s3Url(url)
            .build();
    }
}
