package io.festival.distance.domain.conversation.chatimage.service.serviceimpl;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import org.springframework.stereotype.Component;

@Component
public class ChatImageCreator {

    public ChatImageResponse create(S3UrlResponse s3UrlResponse) {
        return ChatImageResponse.builder()
            .imageUrl(s3UrlResponse.s3Url())
            .build();
    }
}
