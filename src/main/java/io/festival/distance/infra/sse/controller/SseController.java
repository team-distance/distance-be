package io.festival.distance.infra.sse.controller;

import io.festival.distance.auth.jwt.TokenProvider;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.service.SseService;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class SseController {
    private final SseService sseService;
    private final ChatWaitingService chatWaitingService;
    private final TokenProvider jwtTokenProvider;
    @GetMapping(value = "/subscribe/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(
        @PathVariable Long memberId,
        @RequestParam("token") String token
    ) {
        token = token.replace("Bearer ","");
        token = URLDecoder.decode(token, StandardCharsets.UTF_8);
        if (!jwtTokenProvider.validateToken(token,"ACCESS")) {
            log.info("유효하지 않는  큰값입니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Valid Token For member");
        return ResponseEntity.ok(sseService.subscribe(memberId));
    }

    @PostMapping("/send-data/{memberId}")
    public void sendData(@PathVariable Long memberId) {
        sseService.notify(memberId, chatWaitingService.countingWaitingRoom(memberId));
    }
}
