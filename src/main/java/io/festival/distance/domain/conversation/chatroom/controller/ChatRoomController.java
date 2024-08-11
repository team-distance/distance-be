package io.festival.distance.domain.conversation.chatroom.controller;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.dto.PageRequestDto;
import io.festival.distance.domain.conversation.chatroom.service.ChatFacadeService;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.chatroom.dto.response.ChatImageResponse;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.validlogin.ValidUnivCert;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
@CrossOrigin
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatFacadeService chatFacadeService;
    private final ValidUnivCert validUnivCert;
    private final MemberReader memberReader;
    private final ChatRoomReader chatRoomReader;
    private final S3UploadImage s3UploadImage;
    @PostMapping("/create")
    public ResponseEntity<Long> createRoom(@RequestBody ChatRoomDto chatRoomDto,
        Principal principal) {
        return ResponseEntity.ok(chatFacadeService.generateRoom(chatRoomDto, principal, true));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomInfoDto>> showAllRoom(Principal principal) {
        return ResponseEntity.ok(chatRoomService.findAllRoom(principal.getName()));
    }

    @GetMapping("/{chatRoomId}") //채팅방에 들어온 경우
    public ResponseEntity<List<ChatMessageResponseDto>> readMessage(@PathVariable Long chatRoomId,
        Principal principal) {
        Member member = memberReader.findTelNum(principal.getName());
        validUnivCert.checkUnivCert(member);

        return ResponseEntity.ok(
            chatMessageService.markAllMessagesAsRead(chatRoomReader.findChatRoom(chatRoomId),
                member));
    }

    @GetMapping("/{chatRoomId}/message")
    public ResponseEntity<List<ChatMessageResponseDto>> getAllMessage(
        @PathVariable Long chatRoomId,
        PageRequestDto pageRequestDto,
        Principal principal
    ) {
        return ResponseEntity.ok(
            chatMessageService.findAllMessage(chatRoomReader.findChatRoom(chatRoomId),
                pageGenerate(pageRequestDto), principal));
    }

    /**
     * NOTE
     * 로컬 스토리지 비어있을 때 한번만 호출)
     * @param chatRoomId
     * @param principal
     * @return
     */
    @GetMapping("/{chatRoomId}/allmessage")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessage(
        @PathVariable Long chatRoomId,
        Principal principal
    ) {
        Member member = memberReader.findTelNum(principal.getName());
        validUnivCert.checkUnivCert(member);
        return ResponseEntity.ok(
            chatMessageService.findAllChatRoomMessage(chatRoomReader.findChatRoom(chatRoomId),
                principal));
    }

    @GetMapping("/both-agreed/{chatRoomId}")
    public ResponseEntity<Boolean> isAgreed(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatRoomService.getAgreedStatus(chatRoomId));
    }

    @PostMapping("/image")
    public ResponseEntity<ChatImageResponse> sendImage(
        @RequestParam(value = "file") MultipartFile file) {
        S3Response s3Response = s3UploadImage.saveImage(file);
        return ResponseEntity.ok(ChatImageResponse.builder()
            .imageUrl(s3Response.imageUrl())
            .build());
    }


    public static PageRequest pageGenerate(PageRequestDto dto) {
        int page = dto.page();
        int size = dto.size();
        return PageRequest.of(page, size);
    }
}
