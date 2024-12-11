package io.festival.distance.domain.christmas.answer.service;

import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.service.QuestionReader;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerFacade {
    private final QuestionReader questionReader;
    private final AnswerProcessor answerProcessor;
    private final ChatRoomReader chatRoomReader;

    public CurrentResponse findAllAnswer(Long questionId){
        Question question = questionReader.findById(questionId);
        return answerProcessor.generateCurrentResponse(question);
    }

    public CurrentResponse findAnswerInRoom(Long chatRoomId,Long tikiTakaCount){
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        Question question = questionReader.findByChatRoomAndTikiTakaCount(chatRoom, tikiTakaCount);
        return answerProcessor.generateCurrentResponse(question);
    }
}
