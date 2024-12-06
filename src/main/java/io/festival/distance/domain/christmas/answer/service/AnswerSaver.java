package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import io.festival.distance.domain.christmas.answer.repository.AnswerRepository;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerSaver {

    private final AnswerRepository answerRepository;

    @Transactional
    public void save(
        Member member,
        Question question,
        String answer
    ) {
        Answer answerEntity = Answer.builder()
            .answer(answer)
            .member(member)
            .question(question)
            .build();
        answerRepository.save(answerEntity);
    }
}
