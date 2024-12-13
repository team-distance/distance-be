package io.festival.distance.domain.christmas.answer.dto.response;

import io.festival.distance.domain.christmas.question.entity.Question;
import java.util.List;
import lombok.Builder;

@Builder
public record CurrentResponse(
    String question,
    Long questionId,
    List<AnswerResponse> answers

) {

    public static CurrentResponse fromResponse(
        List<AnswerResponse> answers,
        Question question
    ) {
        return CurrentResponse.builder()
            .question(question.getQuestion())
            .answers(answers)
            .questionId(question.getQuestionId())
            .build();
    }
}
