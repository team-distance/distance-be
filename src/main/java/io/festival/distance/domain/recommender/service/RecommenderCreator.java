package io.festival.distance.domain.recommender.service;

import io.festival.distance.domain.recommender.entity.Recommender;
import io.festival.distance.domain.recommender.repository.RecommenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RecommenderCreator {
    private final RecommenderRepository recommenderRepository;

    @Transactional
    public void create(Long referrerId, String referredTel){
        Recommender recommender = Recommender.builder()
            .referrerId(referrerId)
            .referredTel(referredTel)
            .build();
        recommenderRepository.save(recommender);
    }
}
