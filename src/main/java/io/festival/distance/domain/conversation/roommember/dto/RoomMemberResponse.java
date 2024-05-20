package io.festival.distance.domain.conversation.roommember.dto;

import lombok.Builder;

@Builder
public record RoomMemberResponse(long memberId, long opponentId) {

}
