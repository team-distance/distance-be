package io.festival.distance.domain.gps.dto;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.Builder;

@Builder
public record DistanceResponse(Double distance) {

    public static DistanceResponse fromEntity(ChatRoom chatRoom) {
        return DistanceResponse.builder()
            .distance(chatRoom.getDistance())
            .build();
    }
}
