package io.festival.distance.domain.recommender.service;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.recommender.repository.RecommenderRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommenderValidator {

    private final RecommenderRepository recommenderRepository;
    private final MemberReader memberReader;


    public void validRecommend(Long referrerId, String referredTel) {
        isOwned(referrerId, referredTel);
        isAlreadyRecommend(referrerId);
        isExistMember(referredTel);
    }

    public void isAlreadyRecommend(Long referrerId) {
        if (recommenderRepository.existsByReferrerId(referrerId)) {
            throw new DistanceException(ErrorCode.ALREADY_EXIST_RECOMMEND);
        }
    }

    public void isExistMember(String referredTel) {
        memberReader.findTelNum(referredTel);
    }

    public void isOwned(Long referrerId, String referredTel) {
        Member member = memberReader.findMember(referrerId);
        if (member.getTelNum().equals(referredTel)) {
            throw new DistanceException(ErrorCode.NOT_REGISTER_SELF);
        }
    }
}
