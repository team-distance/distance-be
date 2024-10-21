package io.festival.distance.domain.member.service.serviceimpl;

import static io.festival.distance.authuniversity.domain.University.UNIV_MAP;
import static io.festival.distance.authuniversity.domain.University.getDomainByName;

import io.festival.distance.authuniversity.domain.University;
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
    public static final String PREFIX = "#";
    private final MemberRepository memberRepository;
    private final MemberUpdater memberUpdater;
    @Transactional
    public void memberNickNameUpdate(Member member) {
        member.memberNicknameUpdate(
            member.getDepartment() + member.getMbti() + PREFIX + member.getMemberId()
        );
    }

    @Transactional
    public void createMember(Member member){
        memberRepository.save(member);
    }

    public Member getMember(MemberSignDto signDto){
        return Member.builder()
            .password(memberUpdater.modifyPassword(signDto.password()))
            .gender(signDto.gender())
            .telNum(signDto.telNum())
            .authority("ROLE_USER")
            .mbti(signDto.mbti())
            .department(signDto.department())
            .college(signDto.college())
            .school(signDto.school())
            .memberCharacter(signDto.memberCharacter())
            .nickName(signDto.department())
            .reportCount(0)
            .roomCount(3)
            .authUniv(UnivCert.FAILED_1)
            .activated(true)
            .build();
    }
}
