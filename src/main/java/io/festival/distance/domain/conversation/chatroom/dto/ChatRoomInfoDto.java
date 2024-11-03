package io.festival.distance.domain.conversation.chatroom.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomInfoDto {
    private Long chatRoomId;
    private Long opponentMemberId;
    private Integer askedCount;
    private String memberCharacter;
    private String lastMessage;
    private String department;
    private String mbti;
    private LocalDateTime createDt;
    private LocalDateTime modifyDt;
    private String roomStatus;
   /* public ChatRoomInfoDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.createDt = chatRoom.getCreateDt();
        this.modifyDt = chatRoom.getModifyDt();
        //this.roomName = chatRoom.getRoomName();
    }*/
}
