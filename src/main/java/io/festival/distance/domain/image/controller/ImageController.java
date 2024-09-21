package io.festival.distance.domain.image.controller;

import io.festival.distance.domain.image.dto.request.FileRequest;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import io.festival.distance.infra.s3.config.S3PreSignedUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/presigned")
public class ImageController {
    private final S3PreSignedUrlProvider s3PreSignedUrlProvider;
    @PostMapping
    public ResponseEntity<S3UrlResponse> getS3Url(@RequestBody FileRequest fileRequest){
        return ResponseEntity.ok(s3PreSignedUrlProvider.generatePreSignedUrl(fileRequest.fileName()));
    }
}
