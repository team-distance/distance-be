package io.festival.distance.authuniversity.controller;

import io.festival.distance.authuniversity.dto.CertificateDto;
import io.festival.distance.authuniversity.dto.EmailDto;
import io.festival.distance.authuniversity.dto.SchoolNameDto;
import io.festival.distance.authuniversity.service.univmail.AuthenticateMail;
import io.festival.distance.authuniversity.service.univmail.SendSchoolDomain;
import io.festival.distance.authuniversity.usecase.UnivUseCase;
import java.security.Principal;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/univ")
@CrossOrigin
public class MailController {

    private final SendSchoolDomain sendSchoolDomain;
    private final AuthenticateMail authenticateMail;
    private final UnivUseCase univUseCase;
    private String certificationNumber;

    /**
     * NOTE
     * 학교 이름 선택시 해당 도메인 값 return
     */
    @GetMapping("/check/univ-domain")
    public ResponseEntity<List<String>> checkDomain(Principal principal) {
        return ResponseEntity.ok(sendSchoolDomain.sendDomain(principal.getName()));
    }

    /**
     * NOTE
     * 이메일 전송
     */
    @PostMapping("/send/email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailDto emailDto,
        HttpServletRequest request)
        throws MessagingException {
        certificationNumber = univUseCase.execute(emailDto.schoolEmail(),request);
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 인증번호가 일치하는지 확인
     */
    @PostMapping("/certificate/email")
    public ResponseEntity<Void> certificationNumber(
        @RequestBody CertificateDto certificateDto,
        Principal principal
    ) {
        authenticateMail.checkCertificationNumber(
            certificateDto, certificationNumber, principal.getName()
        );
        return ResponseEntity.ok().build();
    }
}