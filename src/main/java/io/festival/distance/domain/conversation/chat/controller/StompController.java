package io.festival.distance.domain.conversation.chat.controller;

import static io.festival.distance.domain.conversation.roommember.service.RoomMemberService.IN_ACTIVE;
import static io.festival.distance.domain.firebase.entity.FcmType.MESSAGE;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.SenderType;
import io.festival.distance.domain.conversation.chat.service.ChatMessageService;
import io.festival.distance.domain.conversation.chat.valid.CheckMessageLength;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.service.ChatRoomSessionService;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingCountDto;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.domain.firebase.entity.FcmType;
import io.festival.distance.domain.firebase.service.FcmService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import java.util.List;
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
public class StompController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomSessionService chatRoomSessionService;
    private final RoomMemberService roomMemberService;
    private final ChatWaitingService chatWaitingService;
    private final CheckMessageLength checkMessageLength;
    private final FcmService fcmService;
    private final MemberService memberService;
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
            ChatRoom chatRoom = chatRoomService.findRoom(roomId);

            // 채팅방 새션 조회
            List<ChatRoomSession> sessionByChatRoom = chatRoomSessionService
                .findSessionByChatRoom(chatRoom); //2개가 나올 듯?
            /**
             *  채팅방을 나가는 경우
             */
            if (chatMessageDto.getPublishType().equals(LEAVE)) {
                if(chatRoom.getRoomStatus().equals(IN_ACTIVE)){
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

                for (ChatRoomSession chatRoomSession : sessionByChatRoom) {
                    Long memberId = chatRoomSession.getMemberId();
                    roomMemberService.updateLastMessage(memberId, messageId,
                        roomId); //가장 최근에 읽은 메시지 수정
                }

                return ResponseEntity.ok(
                    chatMessageService.generateMessage(messageId, 2, chatRoom)
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

            // 나머지 일반적인 경우
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
        Member opponent = memberService.findMember(chatMessageDto.getSenderId());
        Member member = memberService.findMember(chatMessageDto.getReceiverId());
        fcmService.createFcm(opponent, member.getNickName(), "새로운 메시지가 도착했습니다!", MESSAGE);
        //chatMessageService.sendNotificationIfReceiverNotInChatRoom(chatMessageDto, roomId);

        // 채팅 읽음 갱신
        for (ChatRoomSession chatRoomSession : sessionByChatRoom) {
            Long memberId = chatRoomSession.getMemberId();
            roomMemberService.updateLastMessage(memberId, chatMessageId,
                roomId); //가장 최근에 읽은 메시지 수정
        }

        return ResponseEntity.ok(
            chatMessageService.generateMessage(chatMessageId, sessionByChatRoom.size(),
                chatRoom));
    }

    @MessageMapping("/waiting/{memberId}")
    @SendTo("/topic/waiting/{memberId}") // Subscription URL -> 수신
    public ResponseEntity<ChatWaitingCountDto> getWaitingCount(@DestinationVariable Long memberId) {
        return ResponseEntity.ok(chatWaitingService.countingWaitingRoom(memberId));
    }
}