package io.festival.distance.domain.member.validlogin;


import static io.festival.distance.global.exception.ErrorCode.NOT_AUTHENTICATION_STUDENT;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.global.exception.DistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidUnivCert {

    public void checkUnivCert(Member member){
        if(member.getAuthUniv()!= UnivCert.SUCCESS && member.getAuthUniv()!=UnivCert.PENDING){
            throw new DistanceException(NOT_AUTHENTICATION_STUDENT);
        }
    }
}
