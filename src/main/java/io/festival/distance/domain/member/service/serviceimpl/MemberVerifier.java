package io.festival.distance.domain.member.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberVerifier {
    private final MemberReader memberReader;
    public String proofUniv(String telNum){
        return memberReader.findTelNum(telNum).getAuthUniv().getType();
    }

    public boolean verifyNumber(String inputNum, String authenticateNum){
        return authenticateNum.equals(inputNum);
    }
}
