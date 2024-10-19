package io.festival.distance.domain.conversation.chatimage.controller;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import io.festival.distance.domain.conversation.chatimage.service.ChatImageService;
import io.festival.distance.domain.image.dto.request.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@CrossOrigin
public class ChatImageController {
    private final ChatImageService chatImageService;

    @PostMapping
    public ResponseEntity<ChatImageResponse> sendImage(
        @RequestBody FileRequest fileRequest
    ){
        return ResponseEntity.ok(chatImageService.uploadImage(fileRequest.fileName()));
    }
}
