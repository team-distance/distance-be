package io.festival.distance.domain.firebase.service.serviceimpl;

import com.google.firebase.messaging.SendResponse;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.repository.FcmRepository;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmValidator {
    private final FcmReader fcmReader;
    private final FcmUpdater fcmUpdater;
    private final FcmRepository fcmRepository;
    public boolean verifyClientToken(MemberFcmDto memberFcmDto){
        return (
            memberFcmDto.member().getClientToken() == null || memberFcmDto.member()
            .getClientToken().isEmpty()
        );
    }

    public void verifySendFcmMessage(List<SendResponse> responses, List<MemberFcmDto> fcmDtoList){
        IntStream.range(0, responses.size()).forEach(i -> {
            SendResponse sendResponse = responses.get(i);
            MemberFcmDto memberFcmDto = fcmDtoList.get(i);
            if (sendResponse.isSuccessful()) {
                log.info("Success to send message to " + memberFcmDto.member()
                    .getClientToken());
                Fcm fcm = fcmReader.getFcm(memberFcmDto.fcmId());
                fcmUpdater.update(fcm);
            } else {
                log.error(
                    "Failed to send message to " + memberFcmDto.member().getClientToken()
                        + ": " + sendResponse.getException().getMessage());
            }
        });
    }

    public boolean duplicateFcm(Member opponent,String myNickName){
        return !fcmRepository.existByFcmMessage(opponent,myNickName);
    }
}
