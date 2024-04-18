package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.authuniversity.config.mail.SendMailService;
import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import io.festival.distance.authuniversity.dto.CertificateDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
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
        System.out.println(">>>>>>  " + univMailDto.getTempPw());
        return univMailDto.getTempPw();
    }

    @Transactional
    public void checkCertificationNumber(CertificateDto certificateDto, String num2,
        String telNum) {
        Member member = memberService.findByTelNum(telNum);
        if (!certificateDto.number().equals(num2)) {
            throw new DistanceException(ErrorCode.NOT_CORRECT_AUTHENTICATION_NUMBER);
        }
        member.updateAuthUniv(UnivCert.SUCCESS);
        member.updateEmail(certificateDto.schoolEmail());
    }
}
