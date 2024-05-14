package io.festival.distance.domain.membertag.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.service.MemberTagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagReader {
    private final MemberTagService memberTagService;
    public List<MemberTagDto> getTagList(Member member){
        return memberTagService.showTag(member);
    }
}
