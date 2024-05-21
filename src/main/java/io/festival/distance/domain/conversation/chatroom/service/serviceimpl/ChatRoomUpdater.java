package io.festival.distance.domain.conversation.chatroom.service.serviceimpl;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomUpdater {

    @Transactional
    public void update(ChatRoom chatRoom){
        chatRoom.roomInActive();
    }
}
