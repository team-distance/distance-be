package io.festival.distance.domain.conversation.chatroom.validroomcount;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.OptionalLong;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidExistRoom {
    private final RoomMemberRepository roomMemberRepository;
    private final ChatRoomService chatRoomService;

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

    public Optional<Long> ReEnterInActiveRoom(Member me, Member opponent){
        return roomMemberRepository.findByMemberAndMyRoomName(me, opponent.getNickName())
            .map(roomMember -> roomMember.getChatRoom().getChatRoomId());
    }

    @Nullable
    public Long existOpponentChatRoom(Optional<Long> reEnterRoomId, Member me, Member opponent) {
        if(reEnterRoomId.isPresent()){
            Long chatRoomId= reEnterRoomId.get();
            ChatRoom chatRoom = chatRoomService.findRoom(chatRoomId);
            chatRoom.roomActive();
            chatRoomService.saveRoomMember(me,chatRoom, opponent);
            return chatRoomId;
        }
        return null;
    }

    @Nullable
    public Long existMyChatRoom(Optional<Long> reEnterRoomId, Member me, Member opponent) {
        return reEnterRoomId.orElse(null);
    }
}
