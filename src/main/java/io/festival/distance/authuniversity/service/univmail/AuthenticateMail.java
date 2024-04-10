package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.authuniversity.config.mail.SendMailService;
import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class AuthenticateMail {
    private final SendMailService sendMailService;
    private final MemberService memberService;

    public String sendNumber(String schoolEmail) throws MessagingException { //실 서비스
        //schoolEmail= MessageFormat.format("{0}@{1}.ac.kr", schoolEmail,getDomainByName(schoolEmail));
        UnivMailDto univMailDto = sendMailService.createCertificationNumber(schoolEmail);  //번호가 발급
        sendMailService.mailSend(univMailDto); //메일 전송
        //certificationNumber =sendMailService.getTempPassword();
        System.out.println(">>>>>>  "+univMailDto.getTempPw());
        return univMailDto.getTempPw();
    }

    @Transactional
    public boolean checkCertificationNumber(String number,String num2, String telNum){
        Member member = memberService.findByTelNum(telNum);
        if(number.equals(num2)){
            member.updateAuthUniv(UnivCert.SUCCESS);
        }
        return number.equals(num2);
    }
}
