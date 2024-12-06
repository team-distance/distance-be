package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.question.dto.response.QuestionResponse;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.christmas.question.repository.QuestionRepository;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionReader {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question findById(Long questionId){
        return questionRepository.findById(questionId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_ARTIST));
    }


    @Transactional(readOnly = true)
    public List<String> findQuestionsByChatRoom(ChatRoom chatRoom){
        return questionRepository.findAllByChatRoom(chatRoom)
            .stream()
            .map(Question::getQuestion)
            .toList();
    }

    @Transactional(readOnly = true)
    public Question findByChatRoomAndTikiTakaCount(ChatRoom chatRoom, Long tikiTakaCount){
        return questionRepository.findByChatRoomAndTikiTakaCount(chatRoom, tikiTakaCount);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findAllByChatRoom(ChatRoom chatRoom){
        return questionRepository.findAllByChatRoom(chatRoom)
            .stream()
            .map(QuestionResponse::toResponse)
            .toList();
    }
}
