package io.festival.distance.domain.gps.service;

import static io.festival.distance.authuniversity.domain.University.getSchoolLocation;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.gps.dto.DistanceResponse;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.gps.service.serviceimpl.GpsDtoCreator;
import io.festival.distance.domain.gps.service.serviceimpl.GpsReader;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import io.festival.distance.domain.studentcouncil.dto.response.SchoolLocation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GpsService {

    private final MemberReader memberReader;
    private final MemberUpdater memberUpdater;
    private final GpsDtoCreator gpsDtoCreator;
    private final GpsReader gpsReader;
    private final ChatRoomReader chatRoomReader;

    /**
     * NOTE
     * member 테이블의 longitude, latitude 갱신
     */
    @Transactional
    public GpsResponseDto updateMemberGps(String telNum, GpsDto gpsDto) {
        Member member = memberReader.findTelNum(telNum);
        memberUpdater.updateGps(member, gpsDto);
        return gpsDtoCreator.getGpsResponseDto(member);
    }

    @Transactional(readOnly = true)
    public MatchResponseDto matchUser(
        String telNum,
        Double searchRange,
        Boolean isPermitOtherSchool
    ) {
        Member centerUser = memberReader.findTelNum(telNum); //나
        double centerLongitude = centerUser.getLongitude();
        double centerLatitude = centerUser.getLatitude();
        //유저의 위치정보가 0 일때
        if (centerLatitude == 0 || centerLongitude == 0) {
            return gpsReader.getNotFoundPositionMatchList(centerUser);
        }
        // activate, 거리 내에 있는 유저 필터링 -> 랜덤 4명 선택
        return gpsReader.getLoginUserMatchList(
            centerUser,
            searchRange,
            isPermitOtherSchool
        );
    }

    @Transactional(readOnly = true)
    public MatchResponseDto matchNonLoginUser() {
        List<MatchUserDto> matchList = gpsReader.getNonLoginUserMatchList();
        try {
            Collections.shuffle(matchList);
        } catch (Exception e) {
            System.out.println("Exception during shuffle: " + e);
        }
        matchList = matchList.stream().limit(4).collect(Collectors.toList());
        return gpsDtoCreator.getMatchResponseDto(matchList);
    }

    public DistanceResponse callDistance(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        return DistanceResponse.fromEntity(chatRoom);
    }

    public SchoolLocation getLocation(String telNum) {
        Member member = memberReader.findTelNum(telNum);
        return getSchoolLocation(member.getSchool());
    }
}
