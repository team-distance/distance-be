package io.festival.distance.domain.firebase.dto;

import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.entity.FcmType;
import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberFcmDto(Member member,
                           String senderNickName,
                           String message,
                           FcmType type,
                           Long fcmId) {

    public static MemberFcmDto fromEntity(Fcm fcm){
        return MemberFcmDto.builder()
            .fcmId(fcm.getFcmId())
            .member(fcm.getMember())
            .message(fcm.getMessage())
            .senderNickName(fcm.getSenderName())
            .type(fcm.getFcmType())
            .build();
    }
}
