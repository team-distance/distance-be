package io.festival.distance.domain.ip.service.serviceimpl;

import io.festival.distance.domain.ip.repository.MemberIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpValidator {
    private final MemberIpRepository memberIpRepository;

    public boolean isExistMemberIp(String memberIpAddr){
        return memberIpRepository.existsByMemberIpAddr(memberIpAddr);
    }
}
