package io.festival.distance.domain.conversation.roommember.controller;

import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room-member")
@CrossOrigin
public class RoomMemberController {
    private final RoomMemberService roomMemberService;
    @GetMapping("/leave/{chatRoomId}")
    public ResponseEntity<Void> leaveRoom(@PathVariable Long chatRoomId, Principal principal){
        roomMemberService.goOutRoom(chatRoomId,principal);
        return ResponseEntity.ok().build();
    }
}
