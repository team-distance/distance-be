package io.festival.distance.domain.conversation.chatimage.controller;

import io.festival.distance.domain.conversation.chatimage.service.ChatImageService;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@CrossOrigin
public class ChatImageController {
    private final ChatImageService chatImageService;

    @PostMapping
    public ResponseEntity<S3UrlResponse> sendImage(Principal principal){
        return ResponseEntity.ok(chatImageService.uploadImage());
    }
}
