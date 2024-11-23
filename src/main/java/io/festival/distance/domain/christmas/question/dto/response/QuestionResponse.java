package io.festival.distance.domain.christmas.question.dto.response;

import lombok.Builder;

@Builder
public record QuestionResponse(
    String question,
    Long questionId
) {

}
