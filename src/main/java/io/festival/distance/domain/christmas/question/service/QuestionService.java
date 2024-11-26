package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.dto.request.QuestionRequest;
import io.festival.distance.domain.christmas.question.dto.response.QuestionResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ChatRoomReader chatRoomReader;
    private final QuestionValidator questionValidator;
    private final QuestionReader questionReader;
    private final QuestionCreator questionCreator;

    public QuestionResponse create(QuestionRequest questionRequest) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(questionRequest.chatRoomId());
        Question question =
            questionValidator.isExistQuestion(chatRoom, questionRequest.tikiTakaCount())
                ? questionReader.findByChatRoomAndTikiTakaCount(
                chatRoom, questionRequest.tikiTakaCount()
            ) : questionCreator.createAndSaveNewQuestion(
                chatRoom,
                questionRequest.tikiTakaCount()
            );
        return QuestionResponse.builder()
            .questionId(question.getQuestionId())
            .question(question.getQuestion())
            .build();
    }

    public List<QuestionResponse> findAll(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        return questionReader.findAllByChatRoom(chatRoom);
    }
}
