package io.festival.distance.domain.gps.service;

import static io.festival.distance.domain.gps.service.GpsService.calculateDistance;

import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.service.MemberService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsProcessor {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private static final double SEARCH_RANGE = 1000;

    public double getDistance(long id1, long id2) {
        Member member1 = memberService.findMember(id1);
        Member member2 = memberService.findMember(id2);
        if (member1.getLatitude() == 0 || member1.getLongitude() == 0 || member2.getLongitude() == 0
            || member2.getLatitude() == 0) {
            return -1;
        }
        double distance = calculateDistance(member1.getLatitude(), member1.getLongitude(),
            member2.getLatitude(), member2.getLongitude());
        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public MatchResponseDto getMatchingUser(Member centerUser, double centerLatitude,
        double centerLongitude) {
        List<MatchUserDto> matchedUserList = memberRepository.findAll()
            .stream()
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

        return getMatchResponseDto(matchedUserList);
    }

    public MatchResponseDto notFoundUserPosition(Member centerUser) {
        System.out.println("centerUser = " + centerUser.getGender());
        List<MatchUserDto> dtoList = new java.util.ArrayList<>(
            memberRepository.findAll().stream()
                .filter(
                    user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER)
                )
                .filter(
                    user -> user.getLongitude() != 0 || user.getLatitude() != 0
                )
                .filter(
                    user -> !user.getGender().equals(centerUser.getGender())
                )
                .map(
                    user -> MatchUserDto.builder()
                        .memberId(user.getMemberId())
                        .memberProfileDto(memberService.memberProfile(user.getTelNum()))
                        .nickName(user.getNickName())
                        .telNum(user.getTelNum())
                        .build()
                )
                .toList());
        return getMatchResponseDto(dtoList);
    }

    private MatchResponseDto getMatchResponseDto(List<MatchUserDto> dtoList) {
        Collections.shuffle(dtoList);
        List<MatchUserDto> userDtoList = dtoList.stream()
            .map(user ->
                MatchUserDto.builder()
                .memberId(user.memberId())
                .memberProfileDto(memberService.memberProfile(user.telNum()))
                .nickName(user.nickName())
                .build())
            .limit(4)
            .toList();

        return MatchResponseDto.builder()
            .matchedUsers(userDtoList)
            .build();
    }
}
