package io.festival.distance.domain.christmas.answer.entity;

import io.festival.distance.domain.christmas.question.entity.Question;
import io.festival.distance.domain.member.entity.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_answered")
    private Boolean isAnswered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAnswer(String answer) {
        this.answer = answer;
    }
}
