package io.festival.distance.domain.christmas.answer.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CurrentResponse(
    String question,
    List<AnswerResponse> answers

) {

    public static CurrentResponse fromResponse(
        List<AnswerResponse> answers,
        String question
    ) {
        return CurrentResponse.builder()
            .question(question)
            .answers(answers)
            .build();
    }
}
