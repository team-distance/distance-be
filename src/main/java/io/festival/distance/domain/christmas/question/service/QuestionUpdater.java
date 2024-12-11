package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.answer.service.AnswerValidator;
import io.festival.distance.domain.christmas.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionUpdater {
    private final AnswerValidator answerValidator;
    private final QuestionReader questionReader;
    @Transactional
    public void updateStatus(Long questionId){
        Question question = questionReader.findById(questionId);
        if(answerValidator.checkAnswerStatus(question)){
            question.update();
        }
    }
}
