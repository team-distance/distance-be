package io.festival.distance.domain.conversation.chat.controller;

import static io.festival.distance.domain.conversation.chat.entity.SenderType.COME;
import static io.festival.distance.domain.conversation.chat.entity.SenderType.IMAGE;
import static io.festival.distance.domain.conversation.roommember.service.RoomMemberService.IN_ACTIVE;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.dto.ChatSystemResponse;
import io.festival.distance.domain.conversation.chat.entity.SenderType;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chat.valid.CheckMessageLength;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.service.ChatRoomSessionService;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.infra.sqs.SqsService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class  StompController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomSessionService chatRoomSessionService;
    private final RoomMemberService roomMemberService;
    private final CheckMessageLength checkMessageLength;
    private final MemberReader memberReader;
    private final ChatRoomReader chatRoomReader;
    private final SqsService sqsService;
    private static final String LEAVE = "LEAVE";

    @MessageMapping("/chat/{roomId}") //app/chat/{roomId}로 요청이 들어왔을 때 -> 발신
    @SendTo("/topic/chatroom/{roomId}") // Subscription URL -> 수신
    @Transactional
    public ResponseEntity<?> sendMessage(
        @DestinationVariable Long roomId,
        @RequestBody ChatMessageDto chatMessageDto
    ) {
        try {
            checkMessageLength.validMessageLength(chatMessageDto.getChatMessage());
            ChatRoom chatRoom = chatRoomReader.findChatRoom(roomId);

            // 채팅방 새션 조회
            List<ChatRoomSession> sessionByChatRoom = chatRoomSessionService
                .findSessionByChatRoom(chatRoom); //2개가 나올 듯?

            if(chatMessageDto.getPublishType().equals(COME.getSenderType())){
                return ResponseEntity.ok(
                    ChatSystemResponse.builder()
                        .roomStatus(chatRoom.getRoomStatus())
                        .senderType(chatMessageDto.getPublishType())
                        .senderId(chatMessageDto.getSenderId())
                        .build()
                );
            }

            /**
             *  채팅방을 나가는 경우
             */
            if (chatMessageDto.getPublishType().equals(LEAVE)) {
                if (chatRoom.getRoomStatus().equals(IN_ACTIVE)) {
                    roomMemberService.goOutRoom(roomId, chatMessageDto.getReceiverId());
                    return null;
                }

                Member member = roomMemberService.goOutRoom(roomId, chatMessageDto.getReceiverId());

                ChatMessageDto messageDto = ChatMessageDto.builder()
                    .senderId(chatMessageDto.getSenderId())
                    .receiverId(chatMessageDto.getReceiverId())
                    .chatMessage(member.getNickName() + "님이 나갔습니다!")
                    .publishType(LEAVE)
                    .build();

                Long messageId = chatMessageService.createMessage(chatRoom, messageDto,
                    SenderType.SYSTEM);

                sessionByChatRoom = chatRoomSessionService
                    .findSessionByChatRoom(chatRoom); //2개가 나올 듯?
                if (!sessionByChatRoom.isEmpty()) {
                    for (ChatRoomSession chatRoomSession : sessionByChatRoom) {
                        Long memberId = chatRoomSession.getMemberId();
                        if (Objects.equals(memberId, chatMessageDto.getSenderId())) {
                            roomMemberService.updateLastMessage(memberId, messageId,
                                roomId); //가장 최근에 읽은 메시지 수정
                        }
                    }
                }

                return ResponseEntity.ok(
                    chatMessageService.generateMessage(
                        messageId,
                        2,
                        chatRoom,
                        chatMessageDto.getReceiverId(),
                        chatMessageDto.getSenderId()
                    )
                );
            }

            // 전화 요청
            if (chatMessageDto.getPublishType().equals(SenderType.CALL_REQUEST.getSenderType())) {
                return getResponse(roomId, chatMessageDto, chatRoom,
                    sessionByChatRoom);
            }

            //전화 요청 수락
            if (chatMessageDto.getPublishType().equals(SenderType.CALL_RESPONSE.getSenderType())) {
                chatRoomService.setAgreed(chatRoom);
                return getResponse(roomId, chatMessageDto, chatRoom, sessionByChatRoom);
            }

            // 나머지 일반적인 경우 (USER, IMAGE)
            return getResponse(roomId, chatMessageDto, chatRoom,
                sessionByChatRoom);

        } catch (DistanceException e) {
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED)
                .body(e.getErrorCode().getMessage());
        }
    }

    @NotNull
    private ResponseEntity<ChatMessageResponseDto> getResponse(
        Long roomId, ChatMessageDto chatMessageDto, ChatRoom chatRoom,
        List<ChatRoomSession> sessionByChatRoom) {

        Long chatMessageId = chatMessageService.createMessage(chatRoom,
            chatMessageDto, SenderType.of(chatMessageDto.getPublishType())); //메시지 생성
        // receiver 에게 PUSH 알림 전송
        Member opponent = memberReader.findMember(chatMessageDto.getSenderId());
        Member member = memberReader.findMember(chatMessageDto.getReceiverId());
        if(SenderType.of(chatMessageDto.getPublishType()).equals(IMAGE)) {
            sqsService.sendMessage(
                opponent.getClientToken(),
                member.getNickName(),
                "사진을 보냈습니다.",
                chatMessageDto.getChatMessage(),
                member.getMemberCharacter()
            );
        }else {
            sqsService.sendMessage(
                opponent.getClientToken(),
                member.getNickName(),
                chatMessageDto.getChatMessage(),
                null,
                member.getMemberCharacter()
            );
        }
        // 채팅 읽음 갱신
        for (ChatRoomSession chatRoomSession : sessionByChatRoom) {
            Long memberId = chatRoomSession.getMemberId();
            System.out.println("memberId = " + memberId);
            roomMemberService.updateLastMessage(memberId, chatMessageId,
                roomId); //가장 최근에 읽은 메시지 수정
        }

        return ResponseEntity.ok(
            chatMessageService.generateMessage(
                chatMessageId,
                sessionByChatRoom.size(),
                chatRoom,
                member.getMemberId(),
                opponent.getMemberId()
            )
        );
    }
}