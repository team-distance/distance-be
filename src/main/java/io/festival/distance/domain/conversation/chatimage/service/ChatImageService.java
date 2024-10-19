package io.festival.distance.domain.conversation.chatimage.service;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import io.festival.distance.domain.conversation.chatimage.service.serviceimpl.ChatImageCreator;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import io.festival.distance.infra.s3.config.S3PreSignedUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatImageService {
    private final ChatImageCreator chatImageCreator;
    private final S3PreSignedUrlProvider s3PreSignedUrlProvider;
    public ChatImageResponse uploadImage(String fileName) {
        S3UrlResponse s3UrlResponse =
            s3PreSignedUrlProvider.getGeneratePreSignedUrlRequest(fileName);
        return chatImageCreator.create(s3UrlResponse.s3Url());
    }
}
