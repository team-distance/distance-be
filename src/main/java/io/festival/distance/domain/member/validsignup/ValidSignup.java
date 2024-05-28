package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.ip.service.MemberIpService;
import io.festival.distance.domain.member.dto.TelNumRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidSignup {
    private final ValidTelNum validTelNum;
    private final MemberIpService  memberIpService;

    public void validationTelNum(TelNumRequest telNumRequest){
        if (telNumRequest.type().equals("SIGNUP")){
            validTelNum.duplicateCheckTelNum(telNumRequest.telNum());
        }
        if(telNumRequest.type().equals("FIND")){
            validTelNum.notExistTelNum(telNumRequest.telNum());
        }
    }

    public void validationIp(HttpServletRequest request){
        memberIpService.saveIp(request);
    }
}
