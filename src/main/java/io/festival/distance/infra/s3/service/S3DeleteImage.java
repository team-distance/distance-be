package io.festival.distance.infra.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3DeleteImage {

    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucket;
    public void deleteImage(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }


}
