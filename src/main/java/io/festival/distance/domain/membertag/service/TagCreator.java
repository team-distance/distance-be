package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagCreator {
    private final MemberTagRepository memberTagRepository;

    public void createTags(Member member,List<MemberTagDto> memberTagDto){
        List<MemberTag> memberTagList = new ArrayList<>();

        for (MemberTagDto tagDto : memberTagDto) {
            MemberTag memberTag = getTag(member, tagDto.tag());
            memberTagList.add(memberTag);
        }
        memberTagRepository.saveAll(memberTagList);
    }

    public MemberTag getTag(Member member, String tagName){
        return MemberTag.builder()
            .member(member)
            .tagName(tagName)
            .build();
    }

    @Transactional
    public void createTag(MemberTag memberTag){
        memberTagRepository.save(memberTag);
    }
}
