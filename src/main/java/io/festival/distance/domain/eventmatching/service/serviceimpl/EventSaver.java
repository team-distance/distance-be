package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.eventmatching.dto.request.EventMatchRequest;
import io.festival.distance.domain.eventmatching.entity.EventMatch;
import io.festival.distance.domain.eventmatching.repository.EventMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventSaver {

    private final EventMatchRepository eventMatchRepository;
    private final EventCreator eventCreator;

    @Transactional
    public void save(EventMatchRequest eventMatch) {
        EventMatch eventMatch1 = eventCreator.create(eventMatch);
        eventMatchRepository.save(eventMatch1);
    }
}
