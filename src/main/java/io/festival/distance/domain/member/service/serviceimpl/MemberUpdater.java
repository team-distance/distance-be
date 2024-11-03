package io.festival.distance.domain.member.service.serviceimpl;

import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUpdater {
    private final PasswordEncoder encoder;
    private final MemberReader memberReader;
    private final MemberRepository memberRepository;
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

    @Transactional
    public void updateUniv(Member member,UnivCert univCert){
        member.updateAuthUniv(univCert);
    }

    @Transactional
    public void updateGps(Member member, GpsDto gpsDto){
        member.memberGpsUpdate(gpsDto);
    }

    @Transactional
    public void increaseRoomCount(String telNum){
        memberRepository.findByTelNum(telNum)
            .ifPresent(Member::updateRoomCount);
    }
}