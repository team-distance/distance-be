package io.festival.distance.domain.firebase.service.serviceimpl;

import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.repository.FcmRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FcmReader {
    private final FcmRepository fcmRepository;

    @Transactional(readOnly = true)
    public List<Fcm> getFcmList(String message){
        return fcmRepository.SendByFcmMessage(message);
    }

    public Fcm getFcm(Long fcmId){
        return fcmRepository.findById(fcmId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_FCM));
    }
}
