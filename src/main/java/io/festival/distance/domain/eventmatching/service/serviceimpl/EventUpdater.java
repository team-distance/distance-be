package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.eventmatching.entity.EventMatch;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventUpdater {
    @Transactional
    public void update(EventMatch eventMatch, Member member){
        eventMatch.updateMatching(member);
    }
}
