package io.festival.distance.infra.sse.controller;

import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
@CrossOrigin
public class SseController {
    private final SseService sseService;
    private final ChatWaitingService chatWaitingService;

    @GetMapping(value = "/subscribe/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable Long memberId) {
        return ResponseEntity.ok(sseService.subscribe(memberId));
    }

    @PostMapping("/send-data/{memberId}")
    public void sendData(@PathVariable Long memberId) {
        sseService.notify(memberId, chatWaitingService.countingWaitingRoom(memberId));
    }
}
