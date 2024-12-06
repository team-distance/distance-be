package io.festival.distance.domain.christmas.answer.dto.response;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import lombok.Builder;

@Builder
public record AnswerResponse(
    Long answerId,
    String answer,
    Long memberId,
    String memberCharacter,
    String nickName,
    Boolean isAnswered

) {
    public static AnswerResponse toAnswerResponse(Answer answer){
        return AnswerResponse.builder()
            .answer(answer.getAnswer())
            .nickName(answer.getMember().getNickName())
            .memberCharacter(answer.getMember().getMemberCharacter())
            .memberId(answer.getMember().getMemberId())
            .answerId(answer.getAnswerId())
            .isAnswered(answer.getIsAnswered())
            .build();
    }
}
