package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.repository.QuestionRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionSaver {

    private final QuestionRepository questionRepository;

    @Transactional
    public Question save(
        ChatRoom chatRoom,
        String question,
        Long tikiTakaCount
    ) {
        Question questionEntity = Question.builder()
            .question(question)
            .chatRoom(chatRoom)
            .tikiTakaCount(tikiTakaCount)
            .isAnswer(false)
            .build();
        return questionRepository.save(questionEntity);
    }
}
