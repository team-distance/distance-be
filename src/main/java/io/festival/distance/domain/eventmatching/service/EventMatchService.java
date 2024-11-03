package io.festival.distance.domain.eventmatching.service;

import io.festival.distance.domain.eventmatching.dto.request.AdminUpdateRequest;
import io.festival.distance.domain.eventmatching.dto.request.EventMatchRequest;
import io.festival.distance.domain.eventmatching.dto.response.EventMatchListResponse;
import io.festival.distance.domain.eventmatching.dto.response.EventMatchResponse;
import io.festival.distance.domain.eventmatching.entity.EventMatch;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventReader;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventSaver;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventUpdater;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventValidator;
import io.festival.distance.domain.gps.dto.MatchUserDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.infra.sms.SmsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventMatchService {
    private final MemberReader memberReader;
    private final EventValidator eventValidator;
    private final EventSaver eventSaver;
    private final EventReader eventReader;
    private final SmsUtil smsUtil;
    private final EventUpdater eventUpdater;
    public List<EventMatchResponse> findByMemberBySchool(String school, String gender) {
        return memberReader.findMemberListBySchool(school,gender)
            .stream()
            .map(EventMatchResponse::toEventMatchResponse)
            .toList();
    }

    public void createEvent(EventMatchRequest eventMatchRequest) {
        eventValidator.isExist(eventMatchRequest.telNum()); // 이미 이벤트에 참여했는지 확인
        eventSaver.save(eventMatchRequest);
    }

    public MatchUserDto findOpponentProfile(String telNum) {
        Member member = memberReader.findTelNum(telNum);
        EventMatch eventMatch = eventReader.findEventMatch(member.getMemberId());
        Member opponent = memberReader.findMember(eventMatch.getOpponentId());
        return MatchUserDto.fromMember(opponent,memberReader.getMemberProfileDto(opponent));
    }

    public List<EventMatchListResponse> findAllMatingEvent() {
        return eventReader.findAllEvent()
            .stream()
            .map(EventMatchListResponse::toEventListResponse)
            .toList();
    }

    @Transactional
    public void sendAllEventMessage() {
        eventReader.findAllNotSend()
            .forEach(eventMatch -> {
                smsUtil.sendEventMessage(eventMatch.getTelNum(),eventMatch.getOpponentNickname());
                eventMatch.update();
            });
    }

    public void updateEventMatch(AdminUpdateRequest adminUpdateRequest) {
        Member opponent = memberReader.findMember(adminUpdateRequest.opponentId());
        eventUpdater.update(adminUpdateRequest.memberId(),opponent);
    }
}
