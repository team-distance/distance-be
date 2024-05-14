package io.festival.distance.domain.memberhobby.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HobbyReader {
    private final MemberHobbyService memberHobbyService;

    public List<MemberHobbyDto> getHobbyList(Member member){
        return memberHobbyService.showHobby(member);
    }
}
