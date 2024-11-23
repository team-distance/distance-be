package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.service.QuestionReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerFacade {
    private final QuestionReader questionReader;
    private final AnswerReader answerReader;

    @Transactional(readOnly = true)
    public List<CurrentResponse> findAllAnswer(Long questionId){
        Question question = questionReader.findById(questionId);
        return answerReader.findByQuestion(question)
            .stream()
            .map(CurrentResponse::toEntity)
            .toList();
    }
}
