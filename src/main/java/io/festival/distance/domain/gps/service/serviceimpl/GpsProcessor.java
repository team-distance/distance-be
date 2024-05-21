package io.festival.distance.domain.gps.service.serviceimpl;


import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsProcessor {

    private final MemberReader memberReader;
    private final GpsValidator gpsValidator;


    public double getDistance(long myId, long opponentId) {
        Member me = memberReader.findMember(myId);
        Member opponent = memberReader.findMember(opponentId);

        if (gpsValidator.verifyGps(me, opponent)) {
            return -1;
        }
        double distance = calculateDistance(
            me.getLatitude(),
            me.getLongitude(),
            opponent.getLatitude(),
            opponent.getLongitude()
        );
        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * NOTE
     * 두 '점' 사이의 거리를 계산하는 메서드 (Haversine 공식 이용)
     */

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        // 위도, 경도를 라디안으로 변환
        double lat1 = x1 * toRadian;
        double lon1 = y1 * toRadian;
        double lat2 = x2 * toRadian;
        double lon2 = y2 * toRadian;

        // 라디안 단위로 변환된 위도와 경도의 차이
        double deltaLatitude = Math.abs(lat1 - lat2);
        double deltaLongitude = Math.abs(lon1 - lon2);

        // 하버사인 공식
        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
            sinDeltaLat * sinDeltaLat +
                Math.cos(lat1) * Math.cos(lat2) * sinDeltaLng * sinDeltaLng);

        double distance = 2 * radius * Math.asin(squareRoot);
        distance *= 1000; // m 단위로 변환

        return distance;
    }
}