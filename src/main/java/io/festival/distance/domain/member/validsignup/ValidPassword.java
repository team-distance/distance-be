package io.festival.distance.domain.member.validsignup;

import static io.festival.distance.domain.member.exception.MemberErrorCode.NOT_CORRECT_PASSWORD;

import io.festival.distance.domain.member.exception.MemberException;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidPassword {

    private final MemberReader memberReader;
    private final PasswordEncoder encoder;

    public void duplicateCheckPassword(Principal principal, String checkPassword) {
        String password = memberReader.findByTelNum(principal.getName()).getPassword();
        if (!encoder.matches(checkPassword, password)) {
            throw new MemberException(NOT_CORRECT_PASSWORD);
        }
    }
}
