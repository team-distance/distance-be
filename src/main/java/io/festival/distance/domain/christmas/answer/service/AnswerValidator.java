package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerValidator {
    private static final Integer ANSWER_COUNT = 2;

    private final AnswerReader answerReader;

    public void isWriter(String telNum, String checkTelNum) {
        if (!telNum.equals(checkTelNum)) {
            throw new DistanceException(ErrorCode.NOT_REGISTER_SELF);
        }
    }

    public Boolean checkAnswerStatus(Question question) {
        return answerReader.findByQuestion(question).size() == ANSWER_COUNT;
    }

    public void alreadyWrite(Member member, Question question){
        if(answerReader.findByMemberAndQuestion(member, question)){
            throw new DistanceException(ErrorCode.ALREADY_EXIST_MEMBER);
        }
    }
}
