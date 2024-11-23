package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerUpdater {
    private final AnswerReader answerReader;
    private final AnswerValidator answerValidator;

    @Transactional
    public void update(
        String answer,
        String telNum,
        Long answerId
    ){
        Answer answerEntity = answerReader.findById(answerId);
        answerValidator.isWriter(telNum,answerEntity.getMember().getTelNum());
        answerEntity.updateAnswer(answer);
    }
}
