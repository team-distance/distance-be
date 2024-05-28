package io.festival.distance.domain.ip.service.serviceimpl;

import io.festival.distance.domain.ip.repository.MemberIpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IpDeleter {
    private final MemberIpRepository memberIpRepository;

    @Transactional
    @Scheduled(cron = "0 0 9 * * *") //매일 9시에 작동
    public void delete(){
        memberIpRepository.deleteAll();
    }
}
