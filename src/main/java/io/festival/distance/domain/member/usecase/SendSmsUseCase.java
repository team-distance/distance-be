package io.festival.distance.domain.member.usecase;

import io.festival.distance.domain.member.dto.TelNumRequest;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.member.validsignup.ValidSignup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendSmsUseCase {
    private final ValidSignup validSignup;
    private final MemberService memberService;
    public String execute(TelNumRequest telNumRequest){
        validSignup.validationTelNum(telNumRequest);
        return memberService.sendSms(telNumRequest);
    }
}
