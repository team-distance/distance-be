package io.festival.distance.domain.conversation.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatMessageDto {
    private String chatMessage;
    private Long senderId; //상대방
    private Long receiverId; //나
    private String publishType; //USER, LEAVE, CALL_REQUEST, CALL_RESPONSE, IMAGE, COME

    public void updateMessage(String chatMessage){
        this.chatMessage = chatMessage;
    }
}
