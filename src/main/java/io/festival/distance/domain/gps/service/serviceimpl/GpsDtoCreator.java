package io.festival.distance.domain.gps.service.serviceimpl;

import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GpsDtoCreator {
    private final MemberReader memberReader;
    public GpsResponseDto getGpsResponseDto(Member member){
        return GpsResponseDto.builder()
            .memberId(member.getMemberId())
            .latitude(member.getLatitude())
            .longitude(member.getLongitude())
            .build();
    }

    public MatchResponseDto getMatchResponseDto(List<MatchUserDto> matchList){
        return MatchResponseDto.builder()
            .matchedUsers(matchList)
            .build();
    }

    public List<MatchUserDto> getMatchUserDto(List<MatchUserDto> dtoList){
        return dtoList.stream()
            .map(user ->
                MatchUserDto.builder()
                    .memberId(user.memberId())
                    .memberProfileDto(
                        memberReader.getMemberProfileDto(memberReader.findTelNum(user.telNum()))
                    )
                    .nickName(user.nickName())
                    .school(user.school())
                    .reportCount(user.reportCount())
                    .build())
            .limit(4)
            .toList();
    }
}
