package io.festival.distance.domain.ip.service;

import io.festival.distance.domain.ip.service.serviceimpl.IpGenerator;
import io.festival.distance.domain.ip.service.serviceimpl.IpReader;
import io.festival.distance.domain.ip.service.serviceimpl.IpSaver;
import io.festival.distance.domain.ip.service.serviceimpl.IpUpdater;
import io.festival.distance.domain.ip.service.serviceimpl.IpValidator;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberIpService {
    private final IpGenerator ipGenerator;
    private final IpUpdater ipUpdater;
    private final IpSaver ipSaver;
    private final IpReader ipReader;
    private final IpValidator ipValidator;
    @Transactional
    public void saveIp(HttpServletRequest request){
        String memberIpAddr= ipGenerator.generateMemberIp(request);

        if(!ipValidator.isExistMemberIp(memberIpAddr)){
            ipSaver.saveMemberIp(memberIpAddr);
        } else{
            ipUpdater.increaseIpCount(memberIpAddr);
        }

        if(ipReader.getMemberIpCount(memberIpAddr)>3){
            throw new DistanceException(ErrorCode.TOO_MANY_REQUEST);
        }

    }
}
