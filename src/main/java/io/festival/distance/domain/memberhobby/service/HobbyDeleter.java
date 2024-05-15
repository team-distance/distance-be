package io.festival.distance.domain.memberhobby.service;

import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import io.festival.distance.domain.memberhobby.repository.MemberHobbyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HobbyDeleter {
    private final MemberHobbyRepository memberHobbyRepository;

    @Transactional
    public void deleteHobbies(List<MemberHobby> memberHobbyList){
        memberHobbyRepository.deleteAll(memberHobbyList);
    }
}
