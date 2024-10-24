package io.festival.distance.domain.firebase.service;

import com.amazonaws.services.kms.model.DisabledException;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.WebpushNotification;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.FcmType;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmCreator;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmDtoCreator;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmMessageCreator;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmSender;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmValidator;
import io.festival.distance.domain.firebase.service.serviceimpl.FcmWebPushCreator;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    public static final String ADD_WAITING_ROOM_MESSAGE = "새로운 채팅 요청이 들어왔습니다!";
    public static final String REJECT_STUDENT_CARD = "학생증 인증에 실패하였습니다! 마이페이지에서 다시 인증해주세요!";
    public static final String SET_SENDER_NAME = "[관리자]";

    private final FcmDtoCreator fcmDtoCreator;
    private final FcmCreator fcmCreator;
    private final FcmValidator fcmValidator;
    private final FcmSender fcmSender;
    private final FcmWebPushCreator fcmWebPushCreator;
    private final FcmMessageCreator fcmMessageCreator;

/*    @Transactional
    @Scheduled(fixedRate = 30000)
    public void sendUserNotification() {
        log.info("메시지 fcm scheduled 실행!!");
        sendNotification("새로운 메시지가 도착했습니다!");
    }*/

    @Transactional(readOnly = true)
    public void sendNotification(String message) {
        List<MemberFcmDto> fcmDtoList = fcmDtoCreator.createDtoList(message);

        if (!fcmDtoList.isEmpty()) {
            for (MemberFcmDto memberFcmDto : fcmDtoList) {
                if (fcmValidator.verifyClientToken(memberFcmDto)) {
                    log.error(
                        "No token available for member: " + memberFcmDto.member().getMemberId());
                    continue;
                }
                WebpushNotification webpushNotification = fcmWebPushCreator.create(memberFcmDto);

                Message fcmMessage = fcmMessageCreator.createMessage(memberFcmDto,
                    webpushNotification);

                fcmSender.sendSystemNotification(fcmMessage, memberFcmDto.fcmId());
            }
        }
    }

/*    *//**
     * 채팅 대기열 알림
     *//*
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void sendSystemNotification() {
        log.info("scheduled 실행!!");
        sendNotificationForMessage(ADD_WAITING_ROOM_MESSAGE);
        sendNotificationForMessage(REJECT_STUDENT_CARD);
    }*/

    @Transactional(noRollbackFor = DisabledException.class)
    public void createFcm(Member opponent, String title, String message, FcmType fcmType) {
        if (fcmValidator.duplicateFcm(opponent, title)) {
            MemberFcmDto dto = fcmDtoCreator.createDto(opponent,title,message,fcmType);
            fcmCreator.create(dto);
        }
    }

    private void sendNotificationForMessage(String message) {
        List<MemberFcmDto> fcmDtoList = fcmDtoCreator.createDtoList(message);
        if (!fcmDtoList.isEmpty()) {
            // 알림 내용
            MulticastMessage firebaseMessage = createSystemNotification(fcmDtoList);

            try {
                BatchResponse response = fcmSender.sendNotification(firebaseMessage);
                List<SendResponse> responses = response.getResponses();

                fcmValidator.verifySendFcmMessage(responses,fcmDtoList);
            } catch (Exception e) {
                log.error("fcm error>> " + e.getMessage());
            }
        }
    }

    /**
     * 멀티캐스트 발송
     */
    private MulticastMessage createSystemNotification(List<MemberFcmDto> memberFcmDto) {
        List<String> tokens = fcmCreator.createTokens(memberFcmDto);

        MemberFcmDto standardMessage = memberFcmDto.get(0);

        WebpushNotification webpushNotification = fcmWebPushCreator.create(standardMessage);

        return fcmMessageCreator.createMultiCastMessage(tokens,webpushNotification);
    }
}