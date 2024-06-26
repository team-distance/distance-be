package io.festival.distance.domain.conversation.waiting.controller;

import io.festival.distance.domain.conversation.chatroom.service.ChatFacadeService;
import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingDto;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting")
@CrossOrigin
public class ChatWaitingController {
    private final ChatWaitingService chatWaitingService;
    private final ChatFacadeService  chatFacadeService;
    @GetMapping
    public ResponseEntity<List<ChatWaitingDto>> showWaitingRoom(Principal principal){
        return ResponseEntity.ok(chatWaitingService.getWaitingRoom(principal.getName()));
    }

    @GetMapping("/accept/{waitingRoomId}")
    public ResponseEntity<Long> acceptWaiting(@PathVariable Long waitingRoomId,Principal principal){
        return ResponseEntity.ok(chatFacadeService.approveRoom(waitingRoomId,principal));
    }

    @DeleteMapping("/{waitingRoodId}")
    public ResponseEntity<Void> deleteWaitingRoom(@PathVariable Long waitingRoodId,Principal principal){
        chatWaitingService.deleteRoom(waitingRoodId,principal.getName());
        return ResponseEntity.ok().build();
    }
}
