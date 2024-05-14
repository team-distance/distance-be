package io.festival.distance.domain.memberhobby.service.serviceimpl;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import io.festival.distance.domain.memberhobby.repository.MemberHobbyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HobbyCreator {

    private final MemberHobbyRepository memberHobbyRepository;

    @Transactional
    public void create(Member member, List<MemberHobbyDto> memberHobbyDto){
        List<MemberHobby> memberHobby=new ArrayList<>();

        for (MemberHobbyDto hobbyDto : memberHobbyDto) {
            MemberHobby hobby = MemberHobby.builder()
                .hobbyName(hobbyDto.hobby())
                .member(member)
                .build();
            memberHobby.add(hobby);
        }
        memberHobbyRepository.saveAll(memberHobby);
    }
}
