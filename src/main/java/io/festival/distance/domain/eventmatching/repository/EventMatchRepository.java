package io.festival.distance.domain.eventmatching.repository;

import io.festival.distance.domain.eventmatching.entity.EventMatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventMatchRepository extends JpaRepository<EventMatch,Long> {
    Boolean existsByTelNum(String telNum);
    EventMatch findByMemberId(Long memberId);

    @Query("select e from EventMatch e where e.isSend=false")
    List<EventMatch> findAllEventMatching();
}
