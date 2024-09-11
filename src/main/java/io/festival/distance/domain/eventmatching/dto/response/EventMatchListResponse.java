package io.festival.distance.domain.eventmatching.dto.response;

import io.festival.distance.domain.eventmatching.entity.EventMatch;
import lombok.Builder;

@Builder
public record EventMatchListResponse(
    long matchingId,
    long memberId,
    String memberGender,
    String memberTelNum,
    String memberSchool,
    String preferCharacter,
    long opponentId,
    String opponentCharacter,
    String opponentNickname,
    boolean isSend
) {
    public static EventMatchListResponse toEventListResponse(EventMatch eventMatch){
        return EventMatchListResponse.builder()
            .matchingId(eventMatch.getMatchingId())
            .memberId(eventMatch.getMemberId())
            .memberGender(eventMatch.getGender())
            .memberTelNum(eventMatch.getTelNum())
            .memberSchool(eventMatch.getSchool())
            .preferCharacter(eventMatch.getPreferCharacter())
            .opponentId(eventMatch.getOpponentId())
            .opponentCharacter(eventMatch.getOpponentCharacter())
            .opponentNickname(eventMatch.getOpponentNickname())
            .isSend(eventMatch.getIsSend())
            .build();
    }
}
