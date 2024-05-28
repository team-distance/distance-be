package io.festival.distance.authuniversity.usecase;

import io.festival.distance.authuniversity.service.univmail.AuthenticateMail;
import io.festival.distance.domain.member.validsignup.ValidEmail;
import io.festival.distance.domain.member.validsignup.ValidSignup;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnivUseCase {
    private final AuthenticateMail authenticateMail;
    private final ValidEmail validEmail;
    private final ValidSignup validSignup;
    public String execute(String schoolEmail, HttpServletRequest request) throws MessagingException {
        validSignup.validationIp(request);
        validEmail.checkValidEmail(schoolEmail);
        return authenticateMail.sendNumber(schoolEmail);
    }
}
