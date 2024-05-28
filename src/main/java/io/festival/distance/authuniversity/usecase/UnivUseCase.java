package io.festival.distance.authuniversity.usecase;

import io.festival.distance.authuniversity.service.univmail.AuthenticateMail;
import io.festival.distance.domain.member.validsignup.ValidEmail;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnivUseCase {
    private final AuthenticateMail authenticateMail;
    private final ValidEmail validEmail;
    public String execute(String schoolEmail) throws MessagingException {
        validEmail.checkValidEmail(schoolEmail);
        return authenticateMail.sendNumber(schoolEmail);
    }
}
