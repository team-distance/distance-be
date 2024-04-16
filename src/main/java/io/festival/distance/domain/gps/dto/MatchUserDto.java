package io.festival.distance.domain.gps.dto;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchUserDto(long memberId,
                           String nickName,
                           String telNum,
                           MemberProfileDto memberProfileDto) {

    public static MatchUserDto fromMember(Member member, MemberService memberService) {
        // Member로부터 필요한 정보 추출 및 MemberInfoDto 생성 로직 추가
        long memberId = member.getMemberId();
        String nickName = member.getNickName();
        String department = member.getDepartment();
        String telNum = member.getTelNum();
        // 예를 들어 memberService.memberProfile(memberId)로 MemberInfoDto를 생성
        MemberProfileDto memberProfile= memberService.memberProfile(telNum);

        // MatchUserDto 빌더를 사용하여 인스턴스 생성
        return MatchUserDto.builder()
            .memberId(memberId)
            .nickName(nickName)
            .memberProfileDto(memberProfile)
            .telNum(telNum)
            .build();
    }
}
