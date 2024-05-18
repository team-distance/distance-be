package io.festival.distance.domain.gps.service.serviceimpl;


import static io.festival.distance.domain.gps.service.serviceimpl.GpsProcessor.calculateDistance;

import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsReader {

    private static final double SEARCH_RANGE = 1000;
    private final MemberReader memberReader;
    private final GpsDtoCreator gpsDtoCreator;

    public List<MatchUserDto> getNonLoginUserMatchList() {
        return memberReader.findMemberList()
            .stream()
            .filter(
                user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER)
            )
            .map(
                user -> MatchUserDto.builder().memberId(user.getMemberId())
                    .memberProfileDto(memberReader.getMemberProfileDto(user))
                    .nickName(user.getNickName()).build()
            )
            .toList();
    }

    public MatchResponseDto getLoginUserMatchList(
        Member centerUser,
        double centerLatitude,
        double centerLongitude
    ) {
        List<MatchUserDto> userDtoList = memberReader.findMemberList()
            .stream()
            .filter(user -> user.getSchool().equals(centerUser.getSchool()))
            .filter(
                user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER)
                    && !user.getGender().equals(centerUser.getGender())
            )
            .filter(user -> user.getLongitude() != 0 || user.getLatitude() != 0)
            .filter(
                user -> {
                    double userLongitude = user.getLongitude();
                    double userLatitude = user.getLatitude();
                    double distance = calculateDistance(centerLatitude, centerLongitude,
                        userLatitude,
                        userLongitude);
                    return 0 < distance && distance <= SEARCH_RANGE; // 반경 내 user 필터링 (본인 제외)
                }
            )
            .map(users -> MatchUserDto.fromMember(users, memberReader.getMemberProfileDto(users)))
            .toList();
        return getMatchResponseDto(userDtoList);
    }

    public MatchResponseDto getNotFoundPositionMatchList(Member member) {
        List<MatchUserDto> userDtoList = memberReader.findMemberList().stream()
            .filter(
                user -> user.isActivated() && user.getAuthority().equals(Authority.ROLE_USER)
            )
            .filter(
                user -> user.getLongitude() != 0 || user.getLatitude() != 0
            )
            .filter(
                user -> !user.getGender().equals(member.getGender())
            )
            .map(
                user -> MatchUserDto.builder()
                    .memberId(user.getMemberId())
                    .memberProfileDto(memberReader.getMemberProfileDto(user))
                    .nickName(user.getNickName())
                    .telNum(user.getTelNum())
                    .build()
            )
            .toList();
        return getMatchResponseDto(userDtoList);
    }

    public MatchResponseDto getMatchResponseDto(List<MatchUserDto> dtoList) {
        List<MatchUserDto> modifiableList = new ArrayList<>(dtoList);
        try {
            Collections.shuffle(modifiableList);
            //Collections.shuffle(dtoList);
        } catch (Exception e) {
            System.out.println("Exception during shuffle: " + e);
        }
        List<MatchUserDto> userDtoList = gpsDtoCreator.getMatchUserDto(modifiableList);
        return gpsDtoCreator.getMatchResponseDto(userDtoList);
    }
}
