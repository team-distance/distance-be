package io.festival.distance.domain.conversation.chatroom.service.serviceimpl;

import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.roommember.service.serviceimpl.RoomMemberReader;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomDeleter {
    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberReader roomMemberReader;
    private final ChatRoomReader chatRoomReader;
    private final ChatRoomUpdater chatRoomUpdater;
    @Transactional
    public void delete(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    @Transactional
    public void deleteByMemberResign(Member member) {
        roomMemberReader.roomMemberList(member)
            .stream()
            .map(chatRoomReader::findChatRoom)
            .forEach(chatRoom -> {
                if (chatRoomReader.getChatRoomStatus(chatRoom)) {
                    delete(chatRoom.getChatRoomId());
                } else{
                    chatRoomUpdater.update(chatRoom);
                }
            });
    }
}
