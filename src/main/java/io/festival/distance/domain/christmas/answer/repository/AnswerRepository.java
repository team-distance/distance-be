package io.festival.distance.domain.christmas.answer.repository;

import io.festival.distance.domain.christmas.answer.entity.Answer;
import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findAllByQuestion(Question question);

    Boolean existsByMemberAndQuestion(Member member, Question question);
}
