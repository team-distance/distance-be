package io.festival.distance.domain.gps.service;

import static io.festival.distance.domain.gps.service.GpsService.calculateDistance;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsProcessor {

    private final MemberService memberService;

    public double getDistance(long id1, long id2) {
        Member member1 = memberService.findMember(id1);
        Member member2 = memberService.findMember(id2);
        if (member1.getLatitude() == 0 || member1.getLongitude() == 0 || member2.getLongitude() == 0
            || member2.getLatitude() == 0){
            return -1;
        }
        double distance = calculateDistance(member1.getLatitude(), member1.getLongitude(),
            member2.getLatitude(), member2.getLongitude());
        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
