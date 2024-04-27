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
    private static final double SEARCH_RANGE = 2000000000;

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

    /**
     * NOTE
     * member 테이블에서 특정 유저의 latitude, longitude 가져오기
     */
    // @Transactional
    // public MatchResponseDto matchUserGender(String loginId) {
    // 	final double searchRange = 2000000000; // 200m 이내 반경
    //
    // 	Member centerUser =memberService.findByLoginId(loginId);
    // 	double centerLongitude = centerUser.getLongitude();
    // 	double centerLatitude = centerUser.getLatitude();
    //
    // 	// 멤버를 필터링하고, 필터링된 결과를 List<Member>로 변환
    // 	List<MatchUserDto> matchedUserList = memberRepository.findAll().stream()
    // 		.filter(user -> user.isActivated() && !user.getGender().equals(centerUser.getGender())) // activate=true, 다른 성별만 추출
    // 		.filter(user -> {
    // 			double userLongitude = user.getLongitude();
    // 			double userLatitude = user.getLatitude();
    // 			double distance = calculateDistance(centerLatitude, centerLongitude, userLatitude, userLongitude);
    // 			System.out.println(user.getMemberId() + ": " + String.format("%.3f", distance) + " (m)");
    // 			return 0 < distance && distance <= searchRange; // 반경 내 user 필터링 (본인 제외)
    // 		})
    // 		.limit(4) // 최대 4명
    // 		.map(user -> MatchUserDto.builder() // 필요한 정보만 넘기기 위함
    // 			.memberId(user.getMemberId())
    // 			.memberInfoDto(memberService.memberProfile(user.getMemberId()))
    // 			.nickName(user.getNickName())
    // 			.department(user.getDepartment())
    // 			.build())
    // 		.toList();
    // 	return MatchResponseDto.builder()
    // 		.matchedUsers(matchedUserList)
    // 		.build();
    // }
    @Transactional(readOnly = true)
    public MatchResponseDto matchUser(String telNum) {

        Member centerUser = memberService.findByTelNum(telNum); //나
        double centerLongitude = centerUser.getLongitude();
        double centerLatitude = centerUser.getLatitude();

        if (centerLatitude == 0 || centerLongitude == 0) {
            return matchNonLoginUser();
        }

        // activate, 거리 내에 있는 유저 필터링 -> 랜덤 4명 선택
        List<MatchUserDto> matchedUserList = memberRepository.findAll().stream()
            .filter(user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER)
                && !user.getGender().equals(centerUser.getGender()))
            .filter(user -> user.getLongitude() != 0 || user.getLatitude() != 0)
            .filter(user -> {
                double userLongitude = user.getLongitude();
                double userLatitude = user.getLatitude();
                double distance = calculateDistance(centerLatitude, centerLongitude, userLatitude,
                    userLongitude);
                return 0 < distance && distance <= SEARCH_RANGE; // 반경 내 user 필터링 (본인 제외)
            })
            .map(users -> MatchUserDto.fromMember(users, memberService))
            .collect(Collectors.toList());

        Collections.shuffle(matchedUserList); //랜덤

        List<MatchUserDto> matcheList = matchedUserList.stream()
            .limit(4) // 최대 4명
            .map(user -> MatchUserDto.builder()
                .memberId(user.memberId())
                .memberProfileDto(memberService.memberProfile(user.telNum()))
                .nickName(user.nickName())
                .build())
            .toList();

        return MatchResponseDto.builder()
            .matchedUsers(matcheList)
            .build();
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
