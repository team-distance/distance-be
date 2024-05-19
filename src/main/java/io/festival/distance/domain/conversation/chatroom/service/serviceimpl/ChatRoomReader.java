package io.festival.distance.domain.conversation.chatroom.service.serviceimpl;

import static io.festival.distance.domain.conversation.chat.exception.ChatErrorCode.NOT_EXIST_CHATROOM;

import io.festival.distance.domain.conversation.chat.exception.ChatException;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomReader {
    private static final String INACTIVE = "INACTIVE";
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public ChatRoom findChatRoom(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new ChatException(NOT_EXIST_CHATROOM));
    }

    public boolean getChatRoomStatus(ChatRoom chatRoom){
        return chatRoom.getRoomStatus().equals(INACTIVE);
    }
}
