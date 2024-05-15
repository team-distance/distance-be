package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TagDeleter {
    private final MemberTagRepository memberTagRepository;

    @Transactional
    public void deleteTags(List<MemberTag> memberTagList){
        memberTagRepository.deleteAll(memberTagList);
    }
}
