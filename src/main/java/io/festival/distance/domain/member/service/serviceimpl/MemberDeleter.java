package io.festival.distance.domain.member.service.serviceimpl;

import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberDeleter {
    private final MemberRepository memberRepository;

    @Transactional
    public void deleteMember(String telNum){
        memberRepository.deleteByTelNum(telNum);
    }
}
