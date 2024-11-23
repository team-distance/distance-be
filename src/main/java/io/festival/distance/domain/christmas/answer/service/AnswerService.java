package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.dto.request.AnswerRequest;
import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.service.QuestionReader;
import io.festival.distance.domain.christmas.question.service.QuestionUpdater;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionReader questionReader;
    private final MemberReader memberReader;
    private final AnswerSaver answerSaver;
    private final AnswerUpdater answerUpdater;
    private final QuestionUpdater questionUpdater;
    private final AnswerFacade answerFacade;
    private final AnswerValidator answerValidator;
    public List<CurrentResponse> find(Long questionId) {
        return answerFacade.findAllAnswer(questionId);
    }

    public void write(AnswerRequest answerRequest, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        Question question = questionReader.findById(answerRequest.questionId());
        answerValidator.alreadyWrite(member,question);
        answerSaver.save(
            member,
            question,
            answerRequest.answer()
        );
        questionUpdater.updateStatus(question);
    }

    public void update(
        String answer,
        String telNum,
        Long answerId
    ) {
        answerUpdater.update(
            answer,
            telNum,
            answerId
        );
    }
}
