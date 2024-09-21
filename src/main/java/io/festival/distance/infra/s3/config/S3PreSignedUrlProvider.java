package io.festival.distance.infra.s3.config;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3PreSignedUrlProvider {
    @Value("${cloud.aws.credentials.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3;

    public List<S3UrlResponse> generatePreSignedUrl(List<String> fileNames){
         return fileNames.stream()
            .map(this::getGeneratePreSignedUrlRequest)
             .map(S3UrlResponse::toS3UrlResponse)
            .collect(Collectors.toList());
    }

    public String getGeneratePreSignedUrlRequest(String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1시간
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT) // PUT 요청
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}