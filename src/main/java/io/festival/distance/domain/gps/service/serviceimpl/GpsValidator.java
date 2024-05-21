package io.festival.distance.domain.gps.service.serviceimpl;

import static io.festival.distance.authuniversity.domain.University.getIsWomen;
import static io.festival.distance.domain.gps.service.serviceimpl.GpsProcessor.calculateDistance;

import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class GpsValidator {
    public static final double SEARCH_RANGE = 1000;
    public boolean verifyGps(Member me, Member opponent){
        return (
            me.getLatitude() == 0 || me.getLongitude() == 0 ||
            opponent.getLongitude() == 0 || opponent.getLatitude() == 0
        );
    }

    public boolean isSameSchool(Member centerUser, Member member){
        return member.getSchool().equals(centerUser.getSchool());
    }

    public boolean isActivatedMember(Member centerUser){
        return centerUser.isActivated() && centerUser.getAuthority().equals(Authority.ROLE_USER);
    }

    public boolean isWomenSchool(Member centerUser, Member member){
        return getIsWomen(centerUser.getSchool()) ?
            member.getGender().equals(centerUser.getGender())
            : !member.getGender().equals(centerUser.getGender());
    }

    public boolean hasValidLocation(Member centerUser){
        return centerUser.getLongitude() != 0 || centerUser.getLatitude() != 0;
    }

    public boolean isWithinSearchRange(Member centerUser, Member member){
        double userLongitude = member.getLongitude();
        double userLatitude = member.getLatitude();
        double distance = calculateDistance(
            centerUser.getLatitude(),
            centerUser.getLongitude(),
            userLatitude,
            userLongitude
        );
        return 0 < distance && distance <= SEARCH_RANGE;
    }
}
