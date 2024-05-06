package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoomMemberProcessor {
    private final RoomMemberRepository roomMemberRepository;
    @Transactional
    public void updateRoomName(Member member, String nickName){
        List<RoomMember> allByMyRoomName = roomMemberRepository.findAllByMyRoomName(nickName);
        for (RoomMember roomMember : allByMyRoomName) {
            roomMember.updateRoomName(member.getNickName());
        }
    }
}
