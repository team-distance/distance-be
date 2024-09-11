package io.festival.distance.domain.conversation.chatimage.service;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import io.festival.distance.domain.conversation.chatimage.service.serviceimpl.ChatImageCreator;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3UploadImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChatImageService {
    private final S3UploadImage s3UploadImage;
    private final ChatImageCreator chatImageCreator;
    public ChatImageResponse uploadImage(MultipartFile file) {
        S3Response s3Response = s3UploadImage.saveImage(file);
        return chatImageCreator.create(s3Response.imageUrl());
    }
}
