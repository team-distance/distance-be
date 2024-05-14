package io.festival.distance.domain.member.service.serviceimpl;

import static io.festival.distance.domain.member.service.MemberService.PREFIX;

import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCreator {
    private final MemberRepository memberRepository;
    @Transactional
    public void memberNickNameUpdate(Member member) {
        member.memberNicknameUpdate(
            member.getDepartment() + member.getMbti() + PREFIX + member.getMemberId()
        );
    }

    @Transactional
    public Member createMember(Member member){
        return memberRepository.save(member);
    }
}
