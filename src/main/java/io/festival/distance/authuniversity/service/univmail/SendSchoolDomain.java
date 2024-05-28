package io.festival.distance.authuniversity.service.univmail;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static io.festival.distance.authuniversity.domain.University.getDomainByName;

@Service
@RequiredArgsConstructor
public class SendSchoolDomain {
    private final MemberReader memberReader;
    public List<String> sendDomain(String telNum){
        Member member = memberReader.findTelNum(telNum);
        return Arrays.stream(getDomainByName(member.getSchool()).split(","))
            .map(schoolDomain -> MessageFormat.format("@{0}.ac.kr",schoolDomain))
            .toList();
    }
}
