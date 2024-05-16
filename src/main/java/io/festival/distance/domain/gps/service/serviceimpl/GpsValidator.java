package io.festival.distance.domain.gps.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class GpsValidator {
    public boolean verifyGps(Member me, Member opponent){
        return (
            me.getLatitude() == 0 || me.getLongitude() == 0 ||
            opponent.getLongitude() == 0 || opponent.getLatitude() == 0
        );
    }
}
