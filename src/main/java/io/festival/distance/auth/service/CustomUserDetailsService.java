package io.festival.distance.auth.service;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.global.exception.DistanceException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String telNum) {
        Member member = memberRepository.findOneWithAuthoritiesByTelNum(telNum)
                .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
        return createAdmin(telNum,member);
    }

    private User createAdmin(String telNum, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(telNum + " -> 활성화되어 있지 않습니다.");
        }
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(member.getTelNum(),
                member.getPassword(),
                Set.of(grantedAuthorities));
    }
}