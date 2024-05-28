package io.festival.distance.domain.ip.service.serviceimpl;

import io.festival.distance.domain.ip.entity.MemberIp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IpUpdater {
    private final IpReader ipReader;
    @Transactional
    public void increaseIpCount(String memberIpAddr){
        MemberIp memberIp = ipReader.getMemberIp(memberIpAddr);
        memberIp.update();
    }
}
