package io.festival.distance.domain.memberhobby.service;

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
    public void createHobbies(Member member, List<MemberHobbyDto> memberHobbyDto) {
        List<MemberHobby> memberHobby = new ArrayList<>();

        for (MemberHobbyDto hobbyDto : memberHobbyDto) {
            MemberHobby hobby = getHobby(member, hobbyDto);
            memberHobby.add(hobby);
        }
        memberHobbyRepository.saveAll(memberHobby);
    }

    @Transactional
    public void createHobby(MemberHobby memberHobby) {
        memberHobbyRepository.save(memberHobby);
    }

    public MemberHobby getHobby(Member member, MemberHobbyDto memberHobbyDto) {
        return MemberHobby.builder()
            .hobbyName(memberHobbyDto.hobby())
            .member(member)
            .build();
    }
}
