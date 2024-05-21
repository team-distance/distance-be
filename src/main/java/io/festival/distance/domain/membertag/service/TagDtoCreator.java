package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.membertag.entity.MemberTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagDtoCreator {

    public MemberTagDto create(MemberTag memberTag){
        return MemberTagDto.builder()
            .tag(memberTag.getTagName())
            .build();
    }
}
