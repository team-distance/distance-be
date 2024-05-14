package io.festival.distance.domain.member.service.serviceimpl;

import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    public Member findByTelNum(String telNum) {
        return memberRepository.findByTelNum(telNum)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

}
