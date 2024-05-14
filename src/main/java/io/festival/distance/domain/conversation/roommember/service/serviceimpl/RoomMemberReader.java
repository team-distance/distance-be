package io.festival.distance.domain.conversation.roommember.service.serviceimpl;

import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMemberReader {
    private final RoomMemberRepository roomMemberRepository;

    public List<Long> roomMemberList(Member member){
        return roomMemberRepository.findAllByMember(member)
            .stream()
            .map(chatRoomId -> chatRoomId.getChatRoom().getChatRoomId())
            .toList();
    }
}
