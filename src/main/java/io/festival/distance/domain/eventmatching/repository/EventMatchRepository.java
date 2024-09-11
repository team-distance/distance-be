package io.festival.distance.domain.eventmatching.repository;

import io.festival.distance.domain.eventmatching.entity.EventMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventMatchRepository extends JpaRepository<EventMatch,Long> {
    Boolean existsByTelNum(String telNum);
    EventMatch findByMemberId(Long memberId);
}
