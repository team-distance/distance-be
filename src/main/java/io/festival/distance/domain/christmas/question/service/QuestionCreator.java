package io.festival.distance.domain.christmas.question.service;

import io.festival.distance.domain.christmas.answer.service.AnswerSaver;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.dto.RoomInfoDto;
import io.festival.distance.domain.conversation.roommember.service.serviceimpl.RoomMemberReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionCreator {

    private final RandomQuestionGenerator questionGenerator;
    private final QuestionSaver questionSaver;
    private final AnswerSaver answerSaver;
    private final RoomMemberReader roomMemberReader;
    private final static String INITIAL_ANSWER = "";

    public Question createAndSaveNewQuestion(ChatRoom chatRoom, Long tikitakaCount) {
        String question = questionGenerator.generateQuestion(chatRoom);
        Question questionEntity = questionSaver.save(chatRoom, question, tikitakaCount);
        List<RoomInfoDto> roomMember =
            roomMemberReader.findRoomMemberByChatRoom(chatRoom);
        roomMember.forEach(roomInfoDto ->
            answerSaver.save(
                roomInfoDto.member(),
                questionEntity,
                INITIAL_ANSWER,
                false
            )
        );
        return questionEntity;
    }
}
