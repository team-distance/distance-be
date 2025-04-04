package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.dto.response.AnswerResponse;
import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerProcessor {
    private final AnswerReader answerReader;

    @Transactional(readOnly = true)
    public CurrentResponse generateCurrentResponse(Question question) {
        List<AnswerResponse> answerResponses = answerReader.findByQuestion(question)
            .stream()
            .map(AnswerResponse::toAnswerResponse)
            .toList();
        return CurrentResponse.fromResponse(answerResponses, question);
    }
}
