package io.festival.distance.domain.admin.service;

import io.festival.distance.domain.admin.dto.AdminSignUpDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public Long createAdmin(AdminSignUpDto adminSignUpDto) {
        Member member = Member.builder()
            .password(encoder.encode(adminSignUpDto.password()))
            .gender("관리자")
            .telNum(adminSignUpDto.telNum())
            .authority(Authority.ROLE_ADMIN)
            .mbti("ISFJ")
            .nickName("관리자")
            .memberCharacter("운영자")
            .reportCount(0)
            .activated(true)
            .build();
        return memberRepository.save(member).getMemberId();
    }
}
