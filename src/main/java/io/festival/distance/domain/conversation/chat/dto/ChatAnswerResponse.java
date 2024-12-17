package io.festival.distance.domain.conversation.chat.dto;

import lombok.Builder;

@Builder
public record ChatAnswerResponse(
    Long questionId,
    String senderType,
    Long senderId
) {

}
