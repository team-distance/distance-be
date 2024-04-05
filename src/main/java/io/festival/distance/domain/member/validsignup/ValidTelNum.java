package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidTelNum {
    private final MemberRepository memberRepository;

    public void duplicateCheckTelNum(String telNum){
        if(Objects.isNull(telNum))
            throw new DistanceException(ErrorCode.NOT_NULL_NICKNAME);
        if(memberRepository.existsByTelNum(telNum))
            throw new DistanceException(ErrorCode.EXIST_TEL_NUM);
    }
}
