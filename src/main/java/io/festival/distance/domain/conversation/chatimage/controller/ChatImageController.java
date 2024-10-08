package io.festival.distance.domain.conversation.chatimage.controller;

import io.festival.distance.domain.conversation.chatimage.dto.response.ChatImageResponse;
import io.festival.distance.domain.conversation.chatimage.service.ChatImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestParam(value = "file") MultipartFile file
    ){
        return ResponseEntity.ok(chatImageService.uploadImage(file));
    }
}
