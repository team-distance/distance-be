package io.festival.distance.domain.conversation.roommember.service;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_CHATROOM;
import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_ROOM_MEMBER;

import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomDeleter;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.roommember.dto.RoomMemberResponse;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.global.exception.DistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberReader memberReader;
    private final ChatRoomReader chatRoomReader;
    private final ChatRoomDeleter chatRoomDeleter;

    public static final String IN_ACTIVE="INACTIVE";
    @Transactional
    public void updateLastMessage(Long memberId, Long chatMessageId, Long roomId) {
        Member member = memberReader.findMember(memberId);
        ChatRoom chatRoom = chatRoomReader.findChatRoom(roomId);
        RoomMember roomMember = roomMemberRepository.findByMemberAndChatRoom(member, chatRoom)
            .orElseThrow(()-> new DistanceException(NOT_EXIST_CHATROOM));
        roomMember.updateMessageId(chatMessageId);
    }

    public RoomMember findRoomMember(Member member, ChatRoom chatRoom) {
        return roomMemberRepository.findByMemberAndChatRoom(member, chatRoom)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_ROOM_MEMBER));
    }

    @Transactional
    public Member goOutRoom(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        Member member = memberReader.findMember(memberId);

        if (!roomMemberRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new DistanceException(NOT_EXIST_CHATROOM);
        }

        if (chatRoom.getRoomStatus().equals(IN_ACTIVE)) {
            roomMemberRepository.deleteByChatRoomAndMember(chatRoom, member);
            chatRoomDeleter.delete(chatRoomId);
            chatMessageRepository.deleteAllByChatRoom(chatRoom);
            return member;
        }

        chatRoom.roomInActive();
        roomMemberRepository.deleteByChatRoomAndMember(chatRoom, member);
        return member;
    }

    public RoomMemberResponse showRoomMemberId(Long chatRoomId, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        RoomMember roomMember = findRoomMember(member, chatRoom);
        Member opponent = memberReader.findNickName(roomMember.getMyRoomName());
        return RoomMemberResponse.builder()
            .memberId(member.getMemberId())
            .opponentId(opponent.getMemberId())
            .build();
    }
}
