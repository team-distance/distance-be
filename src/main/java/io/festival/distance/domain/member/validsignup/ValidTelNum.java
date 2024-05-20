package io.festival.distance.domain.member.validsignup;

import static io.festival.distance.global.exception.ErrorCode.EXIST_TEL_NUM;
import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.global.exception.DistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidTelNum {
    private final MemberRepository memberRepository;

    public void duplicateCheckTelNum(String telNum){
        if(memberRepository.existsByTelNum(telNum))
            throw new DistanceException(EXIST_TEL_NUM);
    }

    public void notExistTelNum(String telNum){
        if (!memberRepository.existsByTelNum(telNum)){
            throw new DistanceException(NOT_EXIST_MEMBER);
        }
    }
}
