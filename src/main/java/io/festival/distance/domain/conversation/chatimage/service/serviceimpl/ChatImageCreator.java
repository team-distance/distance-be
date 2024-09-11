package io.festival.distance.domain.conversation.chatimage.service.serviceimpl;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ChatImageCreator {
    public ChatImageResponse create(String imageUrl){
        return ChatImageResponse.builder()
            .imageUrl(imageUrl)
            .build();
    }
}
