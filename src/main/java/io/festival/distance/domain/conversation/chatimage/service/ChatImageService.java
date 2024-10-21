package io.festival.distance.domain.conversation.chatimage.service;

import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import io.festival.distance.infra.s3.config.S3PreSignedUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatImageService {
    private final S3PreSignedUrlProvider s3PreSignedUrlProvider;
    public S3UrlResponse uploadImage() {
        return s3PreSignedUrlProvider.getGeneratePreSignedUrlRequest("11");
    }
}
