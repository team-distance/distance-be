package io.festival.distance.domain.memberhobby.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HobbyUpdater {

    private final HobbyReader hobbyReader;
    private final HobbyCreator hobbyCreator;
    private final HobbyDeleter hobbyDeleter;

    @Transactional
    public void memberHobbyUpdate(List<MemberHobbyDto> memberHobbyDtoList, Member member) {
        List<MemberHobby> memberHobbies = hobbyReader.hobbyList(member);

        for (int i = 0; i < memberHobbyDtoList.size(); i++) {
            MemberHobbyDto hobbyDto = memberHobbyDtoList.get(i);
            if (memberHobbies.size() > i) {
                modify(memberHobbies.get(i), hobbyDto.hobby());
            } else {
                MemberHobby hobby = hobbyCreator.getHobby(member, hobbyDto);
                hobbyCreator.createHobby(hobby);
            }
        }

        if (memberHobbies.size() > memberHobbyDtoList.size()) {
            hobbyDeleter.deleteHobbies(
                memberHobbies.subList(memberHobbyDtoList.size(), memberHobbies.size()));
        }
    }

    @Transactional
    public void modify(MemberHobby hobby, String hobbyName) {
        hobby.modifyHobby(hobbyName);
    }
}
