package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import io.festival.distance.domain.christmas.answer.repository.AnswerRepository;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerReader {
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Answer> findByQuestion(Question question){
        return answerRepository.findAllByQuestion(question);
    }

    @Transactional(readOnly = true)
    public Answer findById(Long answerId){
        return answerRepository.findById(answerId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
    }

   @Transactional(readOnly = true)
   public Boolean existByMemberAndQuestion(Member member, Question question){
        return answerRepository.existsByMemberAndQuestion(member, question);
   }

   @Transactional(readOnly = true)
    public Answer findByMemberAndQuestion(Member member, Question question){
        return answerRepository.findByMemberAndQuestion(member, question);
   }
}
