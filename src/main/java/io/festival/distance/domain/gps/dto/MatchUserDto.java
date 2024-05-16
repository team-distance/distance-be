package io.festival.distance.domain.gps.dto;

import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MatchUserDto(
    long memberId,
    String nickName,
    String telNum,
    MemberProfileDto memberProfileDto
) {

    public static MatchUserDto fromMember(Member member, MemberProfileDto memberProfile) {
        return MatchUserDto.builder()
            .memberId(member.getMemberId())
            .nickName(member.getNickName())
            .memberProfileDto(memberProfile)
            .telNum(member.getTelNum())
            .build();
    }
}
