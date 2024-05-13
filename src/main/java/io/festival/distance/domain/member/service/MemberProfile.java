package io.festival.distance.domain.member.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
import io.festival.distance.domain.membertag.service.MemberTagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberProfile {
    private final MemberTagService memberTagService;
    private final MemberHobbyService memberHobbyService;
    @Transactional
    public void profileUpdate(MemberInfoDto memberInfoDto, Member member) {
        member.memberInfoUpdate(memberInfoDto);
    }

    @Transactional
    public void memberTagUpdate(List<MemberTagDto> memberTagDtoList, Member member) {
        memberTagService.modifyTag(member, memberTagDtoList);
    }

    @Transactional
    public void memberHobbyUpdate(List<MemberHobbyDto> memberHobbyDtoList, Member member) {
        memberHobbyService.modifyHobby(member, memberHobbyDtoList);
    }

    public List<MemberHobbyDto> getHobbyList(Member member){
        return memberHobbyService.showHobby(member);
    }

    public List<MemberTagDto> getTagList(Member member){
        return memberTagService.showTag(member);
    }
}
