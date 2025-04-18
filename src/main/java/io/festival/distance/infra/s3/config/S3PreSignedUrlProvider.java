package io.festival.distance.infra.s3.config;

import static io.festival.distance.utils.DateUtil.setExpirationTime;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
            .collect(Collectors.toList());
    }

    public S3UrlResponse getGeneratePreSignedUrlRequest(String fileName) {
        String uuidFileName = UUID.randomUUID().toString();
        Date expiration = setExpirationTime(System.currentTimeMillis());
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, uuidFileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        String s3Url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return S3UrlResponse.toS3UrlResponse(s3Url,uuidFileName);
    }
}