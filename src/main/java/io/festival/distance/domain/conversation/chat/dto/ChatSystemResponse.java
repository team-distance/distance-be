package io.festival.distance.domain.conversation.chat.dto;

import lombok.Builder;

@Builder
public record ChatSystemResponse(
    String roomStatus,
    String senderType,
    Long senderId
) {

}
