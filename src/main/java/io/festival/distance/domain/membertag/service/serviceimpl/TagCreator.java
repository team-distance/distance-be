package io.festival.distance.domain.membertag.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagCreator {
    private final MemberTagRepository memberTagRepository;

    public void create(Member member,List<MemberTagDto> memberTagDto){
        List<MemberTag> memberTagList = new ArrayList<>();

        for (MemberTagDto tagDto : memberTagDto) {
            MemberTag memberTag = MemberTag.builder()
                .tagName(tagDto.tag())
                .member(member)
                .build();
            memberTagList.add(memberTag);
        }
        memberTagRepository.saveAll(memberTagList);
    }
}
