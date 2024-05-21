package io.festival.distance.domain.memberhobby.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import io.festival.distance.domain.memberhobby.repository.MemberHobbyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HobbyReader {

    private final MemberHobbyRepository memberHobbyRepository;
    private final HobbyDtoCreator hobbyDtoCreator;
    @Transactional(readOnly = true)
    public List<MemberHobbyDto> showHobbiesDto(Member member) {
        return hobbyList(member).stream()
            .map(hobbyDtoCreator::create)
            .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<MemberHobby> hobbyList(Member member) {
        return memberHobbyRepository.findAllByMember(member);
    }
}
