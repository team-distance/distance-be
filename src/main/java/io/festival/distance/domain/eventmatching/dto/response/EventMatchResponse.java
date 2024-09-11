package io.festival.distance.domain.eventmatching.dto.response;

import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record EventMatchResponse(
    long memberId,
    String nickname,
    String gender,
    String memberCharacter
) {

    public static EventMatchResponse toEventMatchResponse(Member member){
        return EventMatchResponse.builder()
            .memberId(member.getMemberId())
            .nickname(member.getNickName())
            .gender(member.getGender())
            .memberCharacter(member.getMemberCharacter())
            .build();
    }
}
