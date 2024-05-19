package io.festival.distance.domain.member.validlogin;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidUnivCert {

    public void checkUnivCert(Member member){
        if(member.getAuthUniv()!= UnivCert.SUCCESS && member.getAuthUniv()!=UnivCert.PENDING){
            throw new DistanceException(ErrorCode.NOT_AUTHENTICATION_STUDENT);
        }
    }
}
