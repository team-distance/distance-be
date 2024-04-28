package io.festival.distance.domain.conversation.chat.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.entity.SenderType;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.firebase.dto.FcmDto;
import io.festival.distance.domain.firebase.service.FcmService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final RoomMemberService roomMemberService;
    private final MemberService memberService;
    private final FcmService fcmService;

    private final static Integer INITIAL_COUNT = 2;

    @Transactional
    public Long createMessage(ChatRoom chatRoom, ChatMessageDto chatMessageDto,
        SenderType senderType) {
        Member member = memberService.findMember(chatMessageDto.getReceiverId()); //나
        if (chatMessageDto.getChatMessage().isEmpty()) {
            return null;
        }
        ChatMessage message = ChatMessage.builder()
            .senderId(chatMessageDto.getSenderId())
            .chatMessage(chatMessageDto.getChatMessage())
            .senderName(member.getNickName())
            .unreadCount(INITIAL_COUNT)
            .senderType(senderType)
            .chatRoom(chatRoom)
            .build();

        return chatMessageRepository.save(message).getChatMessageId();
    }

    @Transactional(readOnly = true)
    public boolean checkTiKiTaKa(ChatRoom chatRoom) {
        return chatMessageRepository.checkTiKiTaKa(chatRoom) >= 10;
    }

    /*@Transactional
    public void sendNotificationIfReceiverNotInChatRoom(ChatMessageDto chatMessageDto,
        Long roomId) {
        // 알림을 보낼 떄 필요한 값들
        Member opponent = memberService.findMember(chatMessageDto.getSenderId()); //받는 사람
        String myNickName = memberService.findMember(chatMessageDto.getReceiverId())
            .getNickName(); // 발신자의 닉네임
        // FCM 알림 전송 발송자 닉네임이, chatMessage를 특정 clietnToken에게
        String clientToken = opponent.getClientToken();
        log.info("opponent token>> " + clientToken);
        if (clientToken != null) { // clientToken이 null이 아닐 때만 FCM 알림 전송
            FcmDto fcmDto = FcmDto.builder()
                .clientToken(clientToken)
                .senderNickName(myNickName)
                .message(chatMessageDto.getChatMessage())
                .roomId(roomId)
                .build();
            fcmService.sendNotification(fcmDto);
        } else {
            log.info("client 토큰이 없어서 알림을 안 보냈습니다.");
        }
    }*/

    /**
     * TODO
     * 메소드 네이밍 변경 필요!
     */
    @Transactional
    public ChatMessageResponseDto generateMessage(Long chatMessageId, int currentMemberCount,
        ChatRoom chatRoom) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 메시지"));
        chatMessage.readCountUpdate(currentMemberCount);

        return ChatMessageResponseDto.builder()
            .messageId(chatMessageId)
            .chatMessage(chatMessage.getChatMessage())
            .senderName(chatMessage.getSenderName())
            .senderId(chatMessage.getSenderId())
            .unreadCount(chatMessage.getUnreadCount())
            .sendDt(chatMessage.getCreateDt())
            .checkTiKiTaKa(checkTiKiTaKa(chatRoom))
            .roomStatus(chatRoom.getRoomStatus())
            .senderType(chatMessage.getSenderType().getSenderType())
            .build();
    }

    @Transactional
    public List<ChatMessageResponseDto> markAllMessagesAsRead(ChatRoom chatRoom, Member member) {
        RoomMember roomMember = roomMemberService.findRoomMember(member, chatRoom); //방금 들어온 멤버가

        List<ChatMessage> messages = getChatMessages(chatRoom, roomMember);

        List<ChatMessageResponseDto> responseDtoList = messages.stream()
            .map(ChatMessageResponseDto::new)
            .collect(Collectors.toList());

        // 현재 채팅방에 들어온 사람의 가장 최근에 읽은 곳까지 unReadCount 갱신 (다시 접속했는데 새로운 메세지가 없는 경우)
        if (!responseDtoList.isEmpty()) { //최신 메시지가 있다면
            roomMember.updateMessageId(
                responseDtoList.get(responseDtoList.size() - 1).getMessageId());
        }
        return responseDtoList;
    }

    @Transactional
    public List<ChatMessage> getChatMessages(ChatRoom chatRoom, RoomMember roomMember) {
        Long lastChatMessageId = roomMember.getLastReadMessageId(); //가장 나중에 읽은 메시지 PK값

        List<ChatMessage> messages = chatMessageRepository.findByChatRoomAndChatMessageIdGreaterThan(
            chatRoom, lastChatMessageId);
        messages.forEach(message -> {
            message.readCountUpdate(1);
            chatMessageRepository.save(message);
        });
        return messages;
    }

    /**
     * NOTE
     * 페이징 처리
     * @param chatRoom
     * @param pageRequest
     * @param principal
     * @return
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findAllMessage(ChatRoom chatRoom, PageRequest pageRequest,
        Principal principal) {
        Member member = memberService.findByTelNum(principal.getName());
        RoomMember roomMember = roomMemberService.findRoomMember(member, chatRoom);
        Long lastChatMessageId = roomMember.getLastReadMessageId();
        return chatMessageRepository.findByChatRoomAndChatMessageIdLessThanOrderByCreateDtDesc(
                chatRoom, pageRequest,
                lastChatMessageId)
            .stream()
            .map(ChatMessageResponseDto::new)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findAllChatRoomMessage(ChatRoom chatRoom,
        Principal principal) {
        Member member = memberService.findByTelNum(principal.getName());
        RoomMember roomMember = roomMemberService.findRoomMember(member, chatRoom);

        if (Objects.isNull(roomMember)) {
            throw new DistanceException(ErrorCode.NOT_EXIST_CHATROOM);
        }
        getChatMessages(chatRoom, roomMember);
        return chatMessageRepository.findAllByChatRoomOrderByCreateDtAsc(chatRoom)
            .stream()
            .map(ChatMessageResponseDto::new)
            .toList();
    }
}
