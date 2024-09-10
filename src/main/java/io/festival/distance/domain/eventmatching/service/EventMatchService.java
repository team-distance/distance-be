package io.festival.distance.domain.eventmatching.service;

import io.festival.distance.domain.eventmatching.dto.request.EventMatchRequest;
import io.festival.distance.domain.eventmatching.dto.response.EventMatchResponse;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventSaver;
import io.festival.distance.domain.eventmatching.service.serviceimpl.EventValidator;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventMatchService {
    private final MemberReader memberReader;
    private final EventValidator eventValidator;
    private final EventSaver eventSaver;
    public List<EventMatchResponse> findByMemberBySchool(String school) {
        return memberReader.findMemberListBySchool(school)
            .stream()
            .map(EventMatchResponse::toEventMatchResponse)
            .toList();
    }

    public void createEvent(EventMatchRequest eventMatchRequest) {
        eventValidator.isExist(eventMatchRequest.telNum()); // 이미 이벤트에 참여했는지 확인
        eventSaver.save(eventMatchRequest);
    }
}
