package io.festival.distance.authuniversity.service.univmail;

import static io.festival.distance.authuniversity.domain.University.getDomainByName;
import static io.festival.distance.domain.member.entity.UnivCert.SUCCESS;
import static io.festival.distance.global.exception.ErrorCode.NOT_CORRECT_AUTHENTICATION_NUMBER;

import io.festival.distance.authuniversity.config.mail.SendMailService;
import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import io.festival.distance.authuniversity.dto.CertificateDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.global.exception.DistanceException;
import java.text.MessageFormat;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class AuthenticateMail {

    private final SendMailService sendMailService;
    private final MemberReader memberReader;

    public String sendNumber(String schoolEmail) throws MessagingException { //실 서비스
        String formattedEmail = formatEmail(schoolEmail);
        UnivMailDto univMailDto = makeCertificationNumber(formattedEmail);
        sendEmail(univMailDto); //메일 전송
        return univMailDto.tempPw();
    }

    public String formatEmail(String schoolEmail) {
        return MessageFormat.format("{0}@{1}.ac.kr", schoolEmail, getDomainByName(schoolEmail));
    }

    public UnivMailDto makeCertificationNumber(String formattedEmail) {
        return sendMailService.createCertificationNumber(formattedEmail);
    }

    public void sendEmail(UnivMailDto univMailDto) throws MessagingException {
        sendMailService.mailSend(univMailDto);
    }

    @Transactional
    public void checkCertificationNumber(
        CertificateDto certificateDto,
        String certificationNumber,
        String telNum
    ) {
        verifyCertificationNumber(certificateDto, certificationNumber);
        updateMemberAuthenticationState(telNum, certificateDto.schoolEmail());
    }

    public void verifyCertificationNumber(
        CertificateDto certificateDto,
        String certificationNumber
    ) {
        if (!certificateDto.number().equals(certificationNumber)) {
            throw new DistanceException(NOT_CORRECT_AUTHENTICATION_NUMBER);
        }
    }

    @Transactional
    public void updateMemberAuthenticationState(String telNum, String schoolEmail) {
        Member member = memberReader.findTelNum(telNum);
        member.updateAuthUniv(SUCCESS);
        member.updateEmail(schoolEmail);
    }
}
