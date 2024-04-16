package io.festival.distance.domain.conversation.roommember.repository;


import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sound.midi.MetaMessage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    RoomMember findByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    List<RoomMember> findAllByMember(Member member);

    Long countByMember(Member member);

    Long countByChatRoomChatRoomId(Long chatRoom_chatRoomId);

    void deleteByChatRoomAndMember(ChatRoom chatRoom, Member member);

    @Query("select count (*) from RoomMember room "
        + "where room.member =:member and room.chatRoom.roomStatus = 'ACTIVE'")
    Long checkRoomMemberCount(@Param("member") Member member);

    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
