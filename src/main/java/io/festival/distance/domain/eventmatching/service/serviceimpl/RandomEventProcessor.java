package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomEventProcessor {

    private final MemberReader memberReader;

    public Member getRandomProcess(
        String character,
        String school,
        String gender
    ) {
        List<Member> randomMember = memberReader.findRandomMember(character, school,gender);
        Member member = randomMember.stream()
            .skip((int) (randomMember.size() * Math.random()))
            .findFirst() // 첫 번째 요소를 선택
            .orElse(null);// 멤버가
        if (member == null ) {
            return memberReader.findMemberList().stream()
                .skip((int) (randomMember.size() * Math.random()))
                .findFirst().orElse(null);// 첫 번째 요소를 선택
        }
        return member;
    }
}
