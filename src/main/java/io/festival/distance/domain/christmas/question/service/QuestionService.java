package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.dto.response.QuestionResponse;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ChatRoomReader chatRoomReader;
    private final RandomQuestionGenerator questionGenerator;
    private final QuestionSaver questionSaver;

    public QuestionResponse create(long chatRoomId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        String question = questionGenerator.generateQuestion(chatRoom);
        Long questionId = questionSaver.save(chatRoom, question);
        return QuestionResponse.builder()
            .questionId(questionId)
            .question(question)
            .build();
    }
}
