package io.festival.distance.domain.eventmatching.service.serviceimpl;

import io.festival.distance.domain.eventmatching.dto.request.EventMatchRequest;
import io.festival.distance.domain.eventmatching.entity.EventMatch;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventCreator {
    private final MemberReader memberReader;
    private final RandomEventProcessor eventProcessor;

    public EventMatch create(EventMatchRequest eventMatchRequest){
        Member member = memberReader.findTelNum(eventMatchRequest.telNum());

        // NOTE -> 상대방 랜덤으로 매칭 로직
        Member randomMember = eventProcessor.getRandomProcess(
            eventMatchRequest.preferCharacter(),
            eventMatchRequest.school(),
            member.getGender()
        );

        return EventMatch.builder()
            .memberId(member.getMemberId())
            .school(member.getSchool())
            .telNum(eventMatchRequest.telNum())
            .gender(member.getGender())
            .preferCharacter(eventMatchRequest.preferCharacter())
            .opponentId(randomMember.getMemberId())
            .opponentNickname(randomMember.getNickName())
            .opponentCharacter(randomMember.getMemberCharacter())
            .isSend(false)
            .build();
    }
}
