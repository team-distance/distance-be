package io.festival.distance.infra.s3.service;

import static io.festival.distance.exception.ErrorCode.FAILED_TO_IMAGE_UPLOAD;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.exception.DistanceException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadImage {

    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.credentials.bucket}")
    private String bucket;

    public S3Response saveImage(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            String imageUrl = amazonS3.getUrl(bucket, fileName).toString();
            System.out.println("imageUrl = " + imageUrl);
            return S3Response
                .builder()
                .imageUrl(imageUrl)
                .fileName(fileName)
                .build();
        } catch (IOException e) {
            throw new DistanceException(FAILED_TO_IMAGE_UPLOAD);
        }
    }

    public void deleteImage(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }
}
