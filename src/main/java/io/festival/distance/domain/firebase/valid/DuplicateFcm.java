package io.festival.distance.domain.firebase.valid;

import io.festival.distance.domain.firebase.repository.FcmRepository;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateFcm {
    private final FcmRepository fcmRepository;


    public boolean checkFcm(Member opponent,String myNickName){
        return !fcmRepository.existsByMemberAndSenderName(opponent, myNickName);
    }
}
