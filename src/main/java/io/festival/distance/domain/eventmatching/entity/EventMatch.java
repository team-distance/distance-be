package io.festival.distance.domain.eventmatching.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "event_match")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EventMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long matchingId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "school")
    private String school;

    @Column(name = "telnum")
    private String telNum;

    @Column(name = "gender")
    private String gender;

    @Column(name = "prefer_character")
    private String preferCharacter;

    @Column(name = "opponent_id")
    private Long opponentId;

    @Column(name = "opponent_nickname")
    private String opponentNickname;

    @Column(name = "opponent_character")
    private String opponentCharacter;

    @Column(name = "is_send")
    private Boolean isSend;

}
