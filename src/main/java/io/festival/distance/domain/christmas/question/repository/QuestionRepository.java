package io.festival.distance.domain.christmas.question.repository;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("select count(*) from Question q where q.chatRoom =:chatRoom and q.isAnswer = true ")
    Long findAllByChatRoomAndIsAnswer(@Param(value ="chatRoom") ChatRoom chatRoom);

    List<Question> findAllByChatRoom(ChatRoom chatRoom);
}
