package io.festival.distance.domain.conversation.chatroom.service.serviceimpl;

import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomDeleter {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void delete(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
