package io.festival.distance.domain.member.validsignup;

import static io.festival.distance.domain.member.exception.MemberErrorCode.EXIST_TEL_NUM;
import static io.festival.distance.domain.member.exception.MemberErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.member.exception.MemberException;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidTelNum {
    private final MemberRepository memberRepository;

    public void duplicateCheckTelNum(String telNum){
        if(memberRepository.existsByTelNum(telNum))
            throw new MemberException(EXIST_TEL_NUM);
    }

    public void notExistTelNum(String telNum){
        if (!memberRepository.existsByTelNum(telNum)){
            throw new MemberException(NOT_EXIST_MEMBER);
        }
    }
}
