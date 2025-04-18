package io.festival.distance.domain.conversation.chat.repository;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import java.util.Optional;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.jpa.internal.ManagedFlushCheckerLegacyJpaImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomAndChatMessageIdGreaterThan(ChatRoom chatRoom, Long messageId);

    ChatMessage  findTop1ByChatRoomOrderByCreateDtDesc(ChatRoom chatRoom);

    Integer countByChatRoomAndChatMessageIdGreaterThan(ChatRoom chatRoom, Long lastMessageId);

    Integer countByChatRoom(ChatRoom chatRoom);
    @Query(value = "SELECT COUNT(*) FROM (" +
        "SELECT sender_id, LAG(sender_id) OVER (ORDER BY create_dt) AS prev_sender_id "
        + "FROM chatmessage WHERE chatroom_id =:chatRoomId) AS OrderedMessages "
        + "WHERE sender_id != prev_sender_id", nativeQuery = true)
    Long checkTiKiTaKa(@Param("chatRoomId") ChatRoom chatRoom);

    void deleteAllByChatRoom(ChatRoom chatRoom);

    Page<ChatMessage> findByChatRoomAndChatMessageIdLessThanOrderByCreateDtDesc(ChatRoom chatRoom,
        Pageable pageable,
        Long lastMessageId);

    Page<ChatMessage> findAllByChatRoom(ChatRoom chatRoom, Pageable pageable);

    List<ChatMessage> findAllByChatRoomOrderByCreateDtAsc(ChatRoom chatRoom);
}
