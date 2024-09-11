package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.eventmatching.entity.EventMatch;
import io.festival.distance.domain.eventmatching.repository.EventMatchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventReader {
    private final EventMatchRepository eventMatchRepository;

    @Transactional(readOnly = true)
    public EventMatch findEventMatch(Long memberId){
        return eventMatchRepository.findByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<EventMatch> findAllEvent(){
        return eventMatchRepository.findAll();
    }
}
