package io.festival.distance.domain.firebase.service;

import io.festival.distance.domain.firebase.repository.FcmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FcmProcessor {
    private final FcmRepository fcmRepository;

    @Transactional
    @Scheduled(cron = "0 0 21 * * *") //매일 6시에 작동
    public void deleteFcm(){
        fcmRepository.deleteAllFcmMessage();
    }
}
