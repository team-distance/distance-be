package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.repository.QuestionRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionValidator {

    private final QuestionRepository questionRepository;
    private final QuestionReader questionReader;

    @Transactional(readOnly = true)
    public Long completeQuestionCount(ChatRoom chatRoom) {
        return questionRepository.findAllByChatRoomAndIsAnswer(chatRoom);
    }

    @Transactional(readOnly = true)
    public Boolean isExistQuestion(ChatRoom chatRoom, Long tikiTakaCount) {
        return questionRepository.existsByChatRoomAndTikiTakaCount(chatRoom, tikiTakaCount);
    }

    @Transactional(readOnly = true)
    public void isAnsweredFromQuestion(ChatRoom chatRoom) {
        Question question = questionReader.findByChatRoomOrderById(chatRoom);
        if (!question.getIsAnswer()) {
            throw new DistanceException(ErrorCode.YET_ANSWER_BY_QUESTION);
        }
    }
}
