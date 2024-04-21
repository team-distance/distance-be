package io.festival.distance.domain.firebase.dto;

import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberFcmDto(Member member,
                           String senderNickName,
                           String message) {

    public static MemberFcmDto fromEntity(Fcm fcm){
        return MemberFcmDto.builder()
            .member(fcm.getMember())
            .message(fcm.getMessage())
            .senderNickName("[관리자]")
            .build();
    }
}
