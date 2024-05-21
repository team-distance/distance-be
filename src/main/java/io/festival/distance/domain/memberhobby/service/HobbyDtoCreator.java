package io.festival.distance.domain.memberhobby.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HobbyDtoCreator {

    public MemberHobbyDto create(MemberHobby memberHobby){
        return MemberHobbyDto.builder()
            .hobby(memberHobby.getHobbyName())
            .build();
    }
}
