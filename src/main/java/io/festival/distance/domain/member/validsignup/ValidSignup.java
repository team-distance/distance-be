package io.festival.distance.domain.member.validsignup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidTelNum validLoginId;

    public void validationTelNum(String telNum){
        validLoginId.duplicateCheckTelNum(telNum);
    }
}
