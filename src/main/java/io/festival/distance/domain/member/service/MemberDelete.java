package io.festival.distance.domain.member.service;

import io.festival.distance.auth.refresh.RefreshRepository;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberDelete {
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    @Transactional
    public void deleteMember(String telNum){
        memberRepository.deleteByTelNum(telNum);
    }

    @Transactional
    public void deleteRefreshToken(String telNum){
        refreshRepository.deleteBySubject(telNum);
    }
}
