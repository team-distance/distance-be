package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.dto.MemberSignDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidTelNum validLoginId;

    public boolean validationTelNum(String telNum){
        return validLoginId.duplicateCheckTelNum(telNum);
    }
}
