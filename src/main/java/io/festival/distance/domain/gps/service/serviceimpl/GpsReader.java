package io.festival.distance.domain.gps.service.serviceimpl;

import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsReader {

    private final MemberReader memberReader;
    private final GpsDtoCreator gpsDtoCreator;
    private final GpsValidator gpsValidator;

    /**
     * 비로그인 유저 매칭
     */
    public List<MatchUserDto> getNonLoginUserMatchList() {
        return memberReader.findMemberListByFilter()
            .stream()
            .filter(
                gpsValidator::isActivatedMember
            )
            .map(
                user -> MatchUserDto.fromMember(user,memberReader.getMemberProfileDto(user))
            )
            .collect(Collectors.toList());
    }

    /**
     * 로그인 유저 매칭
     */
    public MatchResponseDto getLoginUserMatchList(
        Member centerUser,
        Double searchRange,
        Boolean isPermitOtherSchool
    ) {
        List<MatchUserDto> userDtoList = memberReader.findMemberListByFilter()
            .stream()
            .filter(
                gpsValidator::isActivatedMember
            )
            .filter(
                user -> isPermitOtherSchool || gpsValidator.isSameSchool(
                    centerUser, user)
            )
            .filter(
                user -> gpsValidator.isWomenSchool(centerUser, user)
            )
            .filter(
                gpsValidator::hasValidLocation
            )
            .filter(
                user -> gpsValidator.isWithinSearchRange(centerUser, user,
                    searchRange)
            )
            .map(users -> MatchUserDto.fromMember(users, memberReader.getMemberProfileDto(users)))
            .collect(Collectors.toList());

        return getMatchResponseDto(userDtoList);
    }

    /**
     * 로그인 & 위치정보가 없는 유저 매칭
     */
    public MatchResponseDto getNotFoundPositionMatchList(Member centerUser) {
        List<MatchUserDto> userDtoList = memberReader.findMemberListByFilter().stream()
            .filter(
                gpsValidator::isActivatedMember
            )
            .filter(
                user -> gpsValidator.isSameSchool(centerUser, user)
            )
            .filter(
                gpsValidator::hasValidLocation
            )
            .filter(
                user -> gpsValidator.isWomenSchool(centerUser, user)
            )
            .map(
                user -> MatchUserDto.fromMember(user,memberReader.getMemberProfileDto(user))
            )
            .collect(Collectors.toList());
        return getMatchResponseDto(userDtoList);
    }

    public MatchResponseDto getMatchResponseDto(List<MatchUserDto> dtoList) {
        try {
            Collections.shuffle(dtoList);
        } catch (Exception e) {
            System.out.println("Exception during shuffle: " + e);
        }
        List<MatchUserDto> userDtoList = gpsDtoCreator.getMatchUserDto(dtoList);
        return gpsDtoCreator.getMatchResponseDto(userDtoList);
    }
}
