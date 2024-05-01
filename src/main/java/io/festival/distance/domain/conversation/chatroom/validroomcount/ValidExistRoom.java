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

    public Long ReEnterRoom(Member me, Member opponent){
        Optional<Long> roomId = roomMemberRepository.findByMemberAndMyRoomName(opponent, me.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId());

        // 첫 번째 쿼리에서 결과를 찾지 못한 경우, 두 번째 쿼리를 시도합니다.
        return roomId.orElseGet(() -> roomMemberRepository.findByMemberAndMyRoomName(me, opponent.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId())
            .orElse(null));  // 두 번째 쿼리에서도 결과가 없을 경우 null을 반환합니다.
        /*return roomMemberRepository.findByMemberAndMyRoomName(opponent, me.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId());

        return roomMemberRepository.findByMemberAndMyRoomName(me,opponent.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId());*/
    }
}
