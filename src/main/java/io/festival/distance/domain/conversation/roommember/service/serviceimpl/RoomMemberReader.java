package io.festival.distance.domain.conversation.roommember.service.serviceimpl;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.dto.RoomInfoDto;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoomMemberReader {

    private final RoomMemberRepository roomMemberRepository;

    public List<Long> roomMemberList(Member member) {
        return roomMemberRepository.findAllByMember(member)
            .stream()
            .map(chatRoomId -> chatRoomId.getChatRoom().getChatRoomId())
            .toList();
    }

    @Transactional(readOnly = true)
    public List<RoomMember> findRoomMemberList(Member member) {
        return roomMemberRepository.findAllByMember(member);
    }

    @Transactional(readOnly = true)
    public List<RoomInfoDto> findRoomMemberByChatRoom(ChatRoom chatRoom) {
        return roomMemberRepository.findAllByChatRoom(chatRoom)
            .stream()
            .map(RoomInfoDto::toResponse)
            .toList();
    }
}
