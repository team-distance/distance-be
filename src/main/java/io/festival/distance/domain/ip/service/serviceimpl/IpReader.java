package io.festival.distance.domain.ip.service.serviceimpl;

import io.festival.distance.domain.ip.entity.MemberIp;
import io.festival.distance.domain.ip.repository.MemberIpRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpReader {

    private final MemberIpRepository memberIpRepository;

    public MemberIp getMemberIp(String memberIpAddr) {
        return memberIpRepository.findByMemberIpAddr(memberIpAddr)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER_IP));
    }

    public Integer getMemberIpCount(String memberIpAddr) {
        return memberIpRepository.findIpCount(memberIpAddr);
    }
}
