package io.festival.distance.domain.member.service;

import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberAccount {
    private final PasswordEncoder encoder;
    public String modifyPassword(String password){
        return encoder.encode(password);
    }

    @Transactional
    public void modifyMemberAccount(String encryptedPassword, Member member){
        member.memberAccountModify(encryptedPassword);
    }
}
