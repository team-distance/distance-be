package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
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
            throw new DistanceException(ErrorCode.NOT_CORRECT_PASSWORD);
        }
    }
}
