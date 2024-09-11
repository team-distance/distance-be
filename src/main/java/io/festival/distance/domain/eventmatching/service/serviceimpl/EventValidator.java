package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.eventmatching.repository.EventMatchRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventValidator {
    private final EventMatchRepository eventMatchRepository;

    public void isExist(String telNum){
        if(eventMatchRepository.existsByTelNum(telNum))
            throw new DistanceException(ErrorCode.ALREADY_EXIST_MEMBER);
    }
}
