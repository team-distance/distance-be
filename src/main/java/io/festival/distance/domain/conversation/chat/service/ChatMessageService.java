package io.festival.distance.domain.conversation.chat.service;

import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_CHATROOM;
import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_MESSAGE;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageDto;
import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.entity.SenderType;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.global.exception.DistanceException;
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
    private final MemberReader memberReader;

    private final static Integer INITIAL_COUNT = 2;

    @Transactional
    public Long createMessage(ChatRoom chatRoom, ChatMessageDto chatMessageDto,
        SenderType senderType) {
        Member member = memberReader.findMember(chatMessageDto.getReceiverId()); //나
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
    public Long checkTiKiTaKa(ChatRoom chatRoom) {
        return chatMessageRepository.checkTiKiTaKa(chatRoom);
    }

    /**
     * TODO
     * 메소드 네이밍 변경 필요!
     */
    @Transactional
    public ChatMessageResponseDto generateMessage(Long chatMessageId, int currentMemberCount,
        ChatRoom chatRoom) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatMessageId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MESSAGE));
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

        return getChatMessageResponseDto(chatRoom, roomMember);
    }

    @Transactional
    public List<ChatMessageResponseDto> getChatMessageResponseDto(ChatRoom chatRoom,
        RoomMember roomMember) {
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
    public List<ChatMessageResponseDto> findAllChatRoomMessage(ChatRoom chatRoom,
        Principal principal) {
        Member member = memberReader.findTelNum(principal.getName());
        RoomMember roomMember = roomMemberService.findRoomMember(member, chatRoom);

        if (Objects.isNull(roomMember)) {
            throw new DistanceException(NOT_EXIST_CHATROOM);
        }

        getChatMessageResponseDto(chatRoom, roomMember);

        return chatMessageRepository.findAllByChatRoomOrderByCreateDtAsc(chatRoom)
            .stream()
            .map(ChatMessageResponseDto::new)
            .toList();
    }

    @Transactional
    public List<ChatMessage> getChatMessages(ChatRoom chatRoom, RoomMember roomMember) {
        Long lastChatMessageId = roomMember.getLastReadMessageId(); //가장 나중에 읽은 메시지 PK값
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomAndChatMessageIdGreaterThan(
            chatRoom, lastChatMessageId
        );
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
        Member member = memberReader.findTelNum(principal.getName());
        RoomMember roomMember = roomMemberService.findRoomMember(member, chatRoom);
        Long lastChatMessageId = roomMember.getLastReadMessageId();
        return chatMessageRepository.findByChatRoomAndChatMessageIdLessThanOrderByCreateDtDesc(
                chatRoom, pageRequest,
                lastChatMessageId)
            .stream()
            .map(ChatMessageResponseDto::new)
            .toList();
    }


}
