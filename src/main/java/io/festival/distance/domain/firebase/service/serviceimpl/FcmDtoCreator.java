package io.festival.distance.domain.firebase.service.serviceimpl;

import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.FcmType;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmDtoCreator {
    private final FcmReader fcmReader;

    public List<MemberFcmDto> createDtoList(String message){
        return fcmReader.getFcmList(message)
            .stream()
            .map(MemberFcmDto::fromEntity)
            .toList();
    }

    public MemberFcmDto createDto(
        Member member,
        String title,
        String message,
        FcmType fcmType) {
        return MemberFcmDto.builder()
            .senderNickName(title)
            .message(message)
            .member(member)
            .type(fcmType)
            .build();
    }
}
