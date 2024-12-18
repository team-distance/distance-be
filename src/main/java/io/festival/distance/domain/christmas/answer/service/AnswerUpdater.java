package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import io.festival.distance.domain.christmas.entryticket.service.EntryTicketSaver;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.service.QuestionReader;
import io.festival.distance.domain.christmas.question.service.QuestionUpdater;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerUpdater {

    private final AnswerReader answerReader;
    private final AnswerValidator answerValidator;
    private final QuestionUpdater questionUpdater;
    private final EntryTicketSaver entryTicketSaver;
    private final QuestionReader questionReader;
    @Transactional
    public void update(
        String answer,
        String telNum,
        Long answerId,
        Long questionId
    ) {
        Answer answerEntity = answerReader.findById(answerId);
        Question question = questionReader.findById(questionId);
        answerValidator.isWriter(telNum, answerEntity.getMember().getTelNum());
        answerEntity.updateAnswer(answer);
        questionUpdater.updateStatus(answerEntity.getQuestion().getQuestionId());
        entryTicketSaver.save(question.getChatRoom());
    }

    @Transactional
    public void writeAnswer(
        Member member,
        Question question,
        String answer
    ) {
        Answer answerEntity = answerReader.findByMemberAndQuestion(member, question);
        answerEntity.updateAnswer(answer);
    }
}
