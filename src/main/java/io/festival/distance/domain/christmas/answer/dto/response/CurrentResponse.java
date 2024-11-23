package io.festival.distance.domain.christmas.answer.dto.response;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import lombok.Builder;

@Builder
public record CurrentResponse(
    String question,
    String answer,
    Long memberId,
    Long answerId
) {
    public static CurrentResponse toEntity(Answer answer){
        return CurrentResponse.builder()
            .question(answer.getQuestion().getQuestion())
            .answer(answer.getAnswer())
            .memberId(answer.getMember().getMemberId())
            .answerId(answer.getAnswerId())
            .build();
    }
}
