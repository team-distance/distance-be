package io.festival.distance.domain.conversation.waiting.dto;

import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record ChatWaitingDto(String department,
                             String mbti,
                             Long loveSenderId,
                             Long loveReceiverId,
                             String memberCharacter,
                             Long waitingRoomId) {
}
