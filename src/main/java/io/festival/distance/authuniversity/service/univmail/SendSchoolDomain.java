package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static io.festival.distance.authuniversity.domain.University.getDomainByName;

@Service
@RequiredArgsConstructor
public class SendSchoolDomain {
    private final MemberReader memberReader;
    public String sendDomain(String telNum){
        Member member = memberReader.findByTelNum(telNum);
        return MessageFormat.format("@{0}.ac.kr",getDomainByName(member.getSchool()));
    }
}
