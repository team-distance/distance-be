package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagUpdater {
    private final TagReader tagReader;
    private final TagCreator tagCreator;
    private final TagDeleter tagDeleter;
    @Transactional
    public void memberTagUpdate(List<MemberTagDto> memberTagDtoList, Member member) {
        List<MemberTag> tagList = tagReader.getTagList(member);

        for (int i = 0; i < memberTagDtoList.size(); i++) {
            MemberTagDto tagDto = memberTagDtoList.get(i);
            if(tagList.size() > i){
                modify(tagList.get(i),tagDto.tag());
            } else{
                MemberTag tag = tagCreator.getTag(member, tagDto.tag());
                tagCreator.createTag(tag);
            }
        }

        if(tagList.size() > memberTagDtoList.size()){
            tagDeleter.deleteTags(tagList.subList(memberTagDtoList.size(), tagList.size()));
        }
    }

    @Transactional
    public void modify(MemberTag memberTag, String tagName){
        memberTag.modifyTag(tagName);
    }
}
