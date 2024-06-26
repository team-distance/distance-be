package io.festival.distance.domain.conversation.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "chatmessage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ChatMessage extends BaseTimeEntity { //채팅 메시지

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long chatMessageId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "chat_message")
    private String chatMessage;

    @Column(name = "unread_count")
    private int unreadCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type")
    private SenderType senderType;

    @JoinColumn(name = "chatroom_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private ChatRoom chatRoom;

    public void readCountUpdate(int currentMemberCount) {
        this.unreadCount -= currentMemberCount;
        if (this.unreadCount < 0) {
            this.unreadCount = 0;
        }
    }

    public void setMessage(String message) {
        this.chatMessage = message;
    }
}
