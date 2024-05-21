package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagReader {
    private final MemberTagRepository memberTagRepository;
    private final TagDtoCreator tagDtoCreator;
    @Transactional(readOnly = true)
    public List<MemberTag> getTagList(Member member){
        return memberTagRepository.findAllByMember(member);
    }

    @Transactional(readOnly = true)
    public List<MemberTagDto> showTagsDto(Member member){
        return getTagList(member).stream()
            .map(tagDtoCreator::create)
            .collect(Collectors.toList());
    }
}
