package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.repository.QuestionRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionValidator {
    private final QuestionRepository questionRepository;
    @Transactional(readOnly = true)
    public Long completeQuestionCount(ChatRoom chatRoom){
        return questionRepository.findAllByChatRoomAndIsAnswer(chatRoom);
    }
}
