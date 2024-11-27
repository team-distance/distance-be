package io.festival.distance.domain.christmas.answer.dto.response;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import lombok.Builder;

@Builder
public record CurrentResponse(
    String question,
    String answer,
    String memberCharacter,
    String nickName,
    Long memberId,
    Long answerId
) {
    public static CurrentResponse toEntity(Answer answer){
        return CurrentResponse.builder()
            .question(answer.getQuestion().getQuestion())
            .answer(answer.getAnswer())
            .nickName(answer.getMember().getNickName())
            .memberCharacter(answer.getMember().getMemberCharacter())
            .memberId(answer.getMember().getMemberId())
            .answerId(answer.getAnswerId())
            .build();
    }
}
