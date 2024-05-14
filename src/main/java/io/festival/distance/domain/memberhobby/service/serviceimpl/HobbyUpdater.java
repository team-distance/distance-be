package io.festival.distance.domain.memberhobby.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HobbyUpdater {
    private final MemberHobbyService memberHobbyService;
    @Transactional
    public void memberHobbyUpdate(List<MemberHobbyDto> memberHobbyDtoList, Member member) {
        memberHobbyService.modifyHobby(member, memberHobbyDtoList);
    }
}
