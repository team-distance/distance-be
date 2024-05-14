package io.festival.distance.domain.member.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUpdater {
    private final PasswordEncoder encoder;
    public String modifyPassword(String password){
        return encoder.encode(password);
    }

    @Transactional
    public void modifyMemberAccount(String encryptedPassword, Member member){
        member.memberAccountModify(encryptedPassword);
    }

    @Transactional
    public void profileUpdate(MemberInfoDto memberInfoDto, Member member) {
        member.memberInfoUpdate(memberInfoDto);
    }

    @Transactional
    public void updateReport(Member member){
        member.increaseReport();
    }

    @Transactional
    public void updateStatus(Member member){
        member.disableAccount();
    }
}
