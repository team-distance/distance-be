package io.festival.distance.domain.conversation.chatroom.service.serviceimpl;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_CHATROOM;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.global.exception.DistanceException;
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
            .orElseThrow(() -> new DistanceException(NOT_EXIST_CHATROOM));
    }

    public boolean getChatRoomStatus(ChatRoom chatRoom){
        return chatRoom.getRoomStatus().equals(INACTIVE);
    }
}
