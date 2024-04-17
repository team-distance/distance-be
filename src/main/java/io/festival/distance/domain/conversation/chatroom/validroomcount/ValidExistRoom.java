package io.festival.distance.domain.conversation.chatroom.validroomcount;

import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.OptionalLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidExistRoom {
    private final RoomMemberRepository roomMemberRepository;

    public Optional<Long> ExistRoom(Member me, Member opponent) { //기존에 있는 방으로 들어갈 때
        return roomMemberRepository.findAllByMember(me)
                .stream()
                .filter(roomMember ->
                    roomMemberRepository.existsByMemberAndChatRoom(opponent,roomMember.getChatRoom()))
                .map(roomMember -> roomMember.getChatRoom().getChatRoomId())
                .findFirst();
    }

    public Optional<Long> ReEnterRoom(Member me, Member opponent){
        return roomMemberRepository.findByMemberAndMyRoomName(opponent, me.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId());
    }
}
