package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomDeleter;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
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
        RoomMember roomMember = roomMemberRepository.findByMemberAndChatRoom(member, chatRoom);
        roomMember.updateMessageId(chatMessageId);
    }

    public RoomMember findRoomMember(Member member, ChatRoom chatRoom) {
        return roomMemberRepository.findByMemberAndChatRoom(member, chatRoom);
    }

    @Transactional
    public Member goOutRoom(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        Member member = memberReader.findMember(memberId);

        if (!roomMemberRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new DistanceException(ErrorCode.NOT_EXIST_CHATROOM);
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
}
