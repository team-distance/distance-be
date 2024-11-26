package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionCreator {
    private final RandomQuestionGenerator questionGenerator;
    private final QuestionSaver questionSaver;
    public Question createAndSaveNewQuestion(ChatRoom chatRoom, Long tikitakaCount) {
        String question = questionGenerator.generateQuestion(chatRoom);
        return questionSaver.save(chatRoom, question, tikitakaCount);
    }
}
