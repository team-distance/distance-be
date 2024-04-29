package io.festival.distance.domain.gps.service;

import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.gps.dto.DistanceResponse;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GpsService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    private final GpsProcessor gpsProcessor;

    /**
     * NOTE
     * member 테이블의 longitude, latitude 갱신
     */
    @Transactional
    public GpsResponseDto updateMemberGps(String telNum, GpsDto gpsDto) {
        Member member = memberService.findByTelNum(telNum);
        member.memberGpsUpdate(gpsDto);
        return GpsResponseDto.builder()
            .memberId(member.getMemberId())
            .latitude(member.getLatitude())
            .longitude(member.getLongitude())
            .build();
    }

    @Transactional(readOnly = true)
    public MatchResponseDto matchUser(String telNum) {

        Member centerUser = memberService.findByTelNum(telNum); //나
        System.out.println("centerUser.getMemberId() = " + centerUser.getMemberId());
        double centerLongitude = centerUser.getLongitude();
        double centerLatitude = centerUser.getLatitude();

        //유저의 위치정보가 0 일때
        if (centerLatitude == 0 || centerLongitude == 0) {
            return gpsProcessor.notFoundUserPosition(centerUser);
        }

        // activate, 거리 내에 있는 유저 필터링 -> 랜덤 4명 선택
        return gpsProcessor.getMatchingUser(centerUser, centerLatitude, centerLongitude);
    }

    @Transactional(readOnly = true)
    public MatchResponseDto matchNonLoginUser() {
        List<MatchUserDto> matcheList = new java.util.ArrayList<>(
            memberRepository.findAll().stream().filter(
                    user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER))
                .map(
                    user -> MatchUserDto.builder().memberId(user.getMemberId())
                        .memberProfileDto(memberService.memberProfile(user.getTelNum()))
                        .nickName(user.getNickName()).build()).toList());

        Collections.shuffle(matcheList); //랜덤

        matcheList = matcheList.stream().limit(4).collect(Collectors.toList());
        return MatchResponseDto.builder()
            .matchedUsers(matcheList)
            .build();
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

    public DistanceResponse callDistance(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .map(DistanceResponse::fromEntity)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_CHATROOM));
    }
}
