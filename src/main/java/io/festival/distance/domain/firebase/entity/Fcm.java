package io.festival.distance.domain.firebase.entity;

import static javax.persistence.EnumType.STRING;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.member.entity.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "fcm")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Fcm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_id")
    private Long fcmId;

    @Column(name = "message")
    private String message;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "is_send")
    private boolean isSend;

    @Enumerated(STRING)
    private FcmType fcmType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateFcm(){
        this.isSend=true;
    }
}
