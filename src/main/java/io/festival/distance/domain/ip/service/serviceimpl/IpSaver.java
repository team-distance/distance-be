package io.festival.distance.domain.ip.service.serviceimpl;

import io.festival.distance.domain.ip.entity.MemberIp;
import io.festival.distance.domain.ip.repository.MemberIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IpSaver {
    private final MemberIpRepository memberIpRepository;

    @Transactional
    public void saveMemberIp(String memberIpAddr){
        MemberIp memberIp = MemberIp.builder()
            .memberIpAddr(memberIpAddr)
            .ipCount(1)
            .build();
        memberIpRepository.save(memberIp);
    }
}
