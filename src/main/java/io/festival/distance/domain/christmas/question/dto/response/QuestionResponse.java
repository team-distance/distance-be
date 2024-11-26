package io.festival.distance.domain.christmas.question.dto.response;

import io.festival.distance.domain.christmas.question.entity.Question;
import lombok.Builder;

@Builder
public record QuestionResponse(
    String question,
    Long questionId
) {
    public static QuestionResponse toResponse(Question question){
        return QuestionResponse.builder()
            .questionId(question.getQuestionId())
            .question(question.getQuestion())
            .build();
    }
}
