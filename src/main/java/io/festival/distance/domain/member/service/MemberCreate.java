package io.festival.distance.domain.member.service;

import static io.festival.distance.domain.member.service.MemberService.PREFIX;

import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
import io.festival.distance.domain.membertag.service.MemberTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCreate {
    private final MemberTagService memberTagService;
    private final MemberHobbyService memberHobbyService;

    @Transactional
    public void memberHobbyUpdate(Member member, MemberSignDto memberSignDto) {
        memberHobbyService.updateHobby(member, memberSignDto.memberHobbyDto());
    }

    @Transactional
    public void memberTagUpdate(Member member, MemberSignDto memberSignDto) {
        memberTagService.updateTag(member, memberSignDto.memberTagDto());
    }

    @Transactional
    public void memberNickNameUpdate(Member member) {
        member.memberNicknameUpdate(
            member.getDepartment() + member.getMbti() + PREFIX + member.getMemberId()
        );
    }
}
