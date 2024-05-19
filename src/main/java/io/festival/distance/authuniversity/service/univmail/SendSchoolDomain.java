package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static io.festival.distance.authuniversity.domain.University.getDomainByName;
import static io.festival.distance.authuniversity.domain.University.getIsWomen;

@Service
@RequiredArgsConstructor
public class SendSchoolDomain {
    private final MemberReader memberReader;
    public String sendDomain(String telNum){
        Member member = memberReader.findByTelNum(telNum);
        return MessageFormat.format("@{0}.ac.kr",getDomainByName(member.getSchool()));
    }
}
