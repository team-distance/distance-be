package io.festival.distance.domain.firebase.service.serviceimpl;

import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.repository.FcmRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FcmCreator {
    private final FcmRepository fcmRepository;

    public Fcm getFcm(MemberFcmDto memberFcmDto){
        return Fcm.builder()
            .message(memberFcmDto.message())
            .senderName(memberFcmDto.senderNickName())
            .isSend(false)
            .member(memberFcmDto.member())
            .fcmType(memberFcmDto.type())
            .build();
    }

    @Transactional
    public void create(MemberFcmDto memberFcmDto){
        Fcm fcm = getFcm(memberFcmDto);
        fcmRepository.save(fcm);
    }

    public List<String> createTokens(List<MemberFcmDto> memberFcmDto){
        return memberFcmDto.stream()
            .map(token -> token.member().getClientToken())
            .toList();
    }
}
