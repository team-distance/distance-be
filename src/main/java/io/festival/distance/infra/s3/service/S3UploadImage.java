package io.festival.distance.infra.s3.service;

import static io.festival.distance.infra.s3.exception.S3ErrorCode.FAILED_TO_IMAGE_UPLOAD;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.exception.S3Exception;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadImage {

    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucket;

    public S3Response saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            String imageUrl = amazonS3.getUrl(bucket, fileName).toString();
            return S3Response
                .builder()
                .imageUrl(imageUrl)
                .fileName(fileName)
                .build();
        } catch (IOException e) {
            throw new S3Exception(FAILED_TO_IMAGE_UPLOAD);
        }
    }
}
