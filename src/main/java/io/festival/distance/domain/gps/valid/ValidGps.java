package io.festival.distance.domain.gps.valid;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidGps {

    public void checkGps(Member member) {
        if (member.getLatitude() == 0 || member.getLongitude() == 0) {
            throw new DistanceException(ErrorCode.NOT_EXIST_GPS);
        }
    }
}
