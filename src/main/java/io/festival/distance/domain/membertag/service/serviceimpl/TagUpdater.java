package io.festival.distance.domain.membertag.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.service.MemberTagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagUpdater {
    private final MemberTagService memberTagService;

    @Transactional
    public void memberTagUpdate(List<MemberTagDto> memberTagDtoList, Member member) {
        memberTagService.modifyTag(member, memberTagDtoList);
    }
}
