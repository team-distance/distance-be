package io.festival.distance.domain.christmas.question.service;

import static io.festival.distance.utils.QuestionList.questionList;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomQuestionGenerator {

    private final QuestionReader questionReader;

    /**
     * TODO -> 랜덤으로 질문 생성 구현
     */
    public String generateQuestion(ChatRoom chatRoom) {
        List<String> questions = questionReader.findQuestionsByChatRoom(chatRoom);

        return questionList()
            .stream()
            .filter(
                question -> !questions.contains(question)
            )
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(), list -> {
                        Collections.shuffle(list);
                        return list.isEmpty() ? "질문이 없습니다." : list.get(0);
                    }
                )
            );
    }
}
