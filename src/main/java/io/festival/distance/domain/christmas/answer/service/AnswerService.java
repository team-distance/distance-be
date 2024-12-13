package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.dto.request.AnswerRequest;
import io.festival.distance.domain.christmas.answer.dto.request.AnswerUpdateRequest;
import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.entryticket.service.EntryTicketSaver;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.service.QuestionReader;
import io.festival.distance.domain.christmas.question.service.QuestionUpdater;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionReader questionReader;
    private final MemberReader memberReader;
    private final AnswerUpdater answerUpdater;
    private final QuestionUpdater questionUpdater;
    private final AnswerFacade answerFacade;
    private final EntryTicketSaver entryTicketSaver;
    public CurrentResponse find(Long questionId) {
        return answerFacade.findAllAnswer(questionId);
    }

    public CurrentResponse find(Long chatRoomId,Long tikiTakaCount) {
       return answerFacade.findAnswerInRoom(chatRoomId, tikiTakaCount);
    }

    public Boolean write(AnswerRequest answerRequest, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        Question question = questionReader.findById(answerRequest.questionId());
        answerUpdater.writeAnswer(member,question,answerRequest.answer());
        questionUpdater.updateStatus(answerRequest.questionId());
        return entryTicketSaver.save(question.getChatRoom(),member.getTelNum());
    }

    public void update(
        AnswerUpdateRequest answerUpdateRequest,
        String telNum,
        Long answerId
    ) {
        answerUpdater.update(
            answerUpdateRequest.answer(),
            telNum,
            answerId,
            answerUpdateRequest.questionId()
        );
    }
}
