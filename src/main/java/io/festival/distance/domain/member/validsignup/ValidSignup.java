package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.dto.TelNumRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidTelNum validTelNum;

    public void validationTelNum(TelNumRequest telNumRequest){
        if (telNumRequest.type().equals("SIGNUP")){
            validTelNum.duplicateCheckTelNum(telNumRequest.telNum());
        }
        if(telNumRequest.type().equals("FIND")){
            validTelNum.notExistTelNum(telNumRequest.telNum());
        }
    }
}
