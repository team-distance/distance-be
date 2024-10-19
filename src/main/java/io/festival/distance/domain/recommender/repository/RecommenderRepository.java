package io.festival.distance.domain.recommender.repository;

import io.festival.distance.domain.recommender.entity.Recommender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommenderRepository extends JpaRepository<Recommender,Long> {
    Boolean existsByReferrerId(Long referrerId);
}
