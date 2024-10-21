package io.festival.distance.domain.recommender.service;

import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommenderProcessor {
    private final RecommenderCreator recommenderCreator;
    private final RecommenderValidator recommenderValidator;
    private final MemberUpdater memberUpdater;

    public void recommendGenerate(Long referrerId, String referredTel){
        recommenderValidator.validRecommend(referrerId, referredTel);
        recommenderCreator.create(referrerId,referredTel);
        memberUpdater.increaseRoomCount(referredTel);
    }
}
