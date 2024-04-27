package io.festival.distance.domain.firebase.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.entity.FcmType;
import io.festival.distance.domain.firebase.repository.FcmRepository;
import io.festival.distance.domain.firebase.valid.DuplicateFcm;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final DuplicateFcm duplicateFcm;
    private final FcmRepository fcmRepository;
    public static final String ADD_WAITING_ROOM_MESSAGE = "새로운 채팅 요청이 들어왔습니다!";
    public static final String REJECT_STUDENT_CARD = "학생증 인증에 실패하였습니다!";
    public static final String SET_SENDER_NAME = "[관리자]";

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void sendUserNotification() {
        log.info("메시지 fcm scheduled 실행!!");
        sendNotification("새로운 메시지가 도착했습니다!");
    }

    @Transactional(readOnly = true)
    public void sendNotification(String message) {
        List<MemberFcmDto> fcmDtoList = fcmRepository.SendByFcmMessage(message)
            .stream()
            .map(MemberFcmDto::fromEntity)
            .toList();

        if (!fcmDtoList.isEmpty()) {
            for (MemberFcmDto memberFcmDto : fcmDtoList) {
                if (memberFcmDto.member().getClientToken() == null || memberFcmDto.member()
                    .getClientToken().isEmpty()) {
                    log.error(
                        "No token available for member: " + memberFcmDto.member().getMemberId());
                    continue;
                }
                WebpushNotification webpushNotification = WebpushNotification.builder()
                    .setTitle(memberFcmDto.senderNickName())
                    .setBody(memberFcmDto.message())
                    .setImage(
                        "https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
                    .build();

                Message fcmMessage = Message.builder()
                    .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(webpushNotification)
                        .build())
                    .setToken(memberFcmDto.member().getClientToken())
                    .build();

                createNotificationContent(fcmMessage, memberFcmDto.fcmId());
            }
        }
    }

    private void createNotificationContent(Message message, Long fcmId) {
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("Success to send message");
            getFcm(fcmId).updateFcm();
        } catch (Exception e) {
            log.error(
                "Failed to send message");
        }
    }

    /**
     * 채팅 대기열 알림
     */
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void sendSystemNotification() {
        log.info("scheduled 실행!!");
        sendNotificationForMessage(ADD_WAITING_ROOM_MESSAGE);
        sendNotificationForMessage(REJECT_STUDENT_CARD);
    }

    private void sendNotificationForMessage(String message) {
        List<MemberFcmDto> fcmDtoList = fcmRepository.SendByFcmMessage(message)
            .stream()
            .map(MemberFcmDto::fromEntity)
            .toList();

        if (!fcmDtoList.isEmpty()) {
            // 알림 내용
            MulticastMessage firebaseMessage = createSystemNotification(fcmDtoList);

            try {
                BatchResponse response = FirebaseMessaging.getInstance()
                    .sendEachForMulticastAsync(firebaseMessage)
                    .get();
                List<SendResponse> responses = response.getResponses();

                IntStream.range(0, responses.size()).forEach(i -> {
                    SendResponse sendResponse = responses.get(i);
                    MemberFcmDto memberFcmDto = fcmDtoList.get(i);
                    if (sendResponse.isSuccessful()) {
                        log.info("Success to send message to " + memberFcmDto.member()
                            .getClientToken());
                        getFcm(memberFcmDto.fcmId()).updateFcm();
                    } else {
                        log.error(
                            "Failed to send message to " + memberFcmDto.member().getClientToken()
                                + ": " + sendResponse.getException().getMessage());
                    }
                });
            } catch (Exception e) {
                log.error("fcm error>> " + e.getMessage());
            }
        }
    }

    /**
     * 채팅 대기열 알림
     */
    private MulticastMessage createSystemNotification(List<MemberFcmDto> memberFcmDto) {
        List<String> tokens = memberFcmDto.stream()
            .map(token -> token.member().getClientToken())
            .toList();

        MemberFcmDto standardMessage = memberFcmDto.get(0);

        WebpushNotification webpushNotification = WebpushNotification.builder()
            .setTitle(standardMessage.senderNickName())
            .setBody(standardMessage.message())
            .setImage(
                "https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
            .build();

        return MulticastMessage.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(webpushNotification)
                .build())
            .addAllTokens(tokens)
            .build();
    }

    public void saveFcm(MemberFcmDto memberFcmDto) {
        Fcm fcm = Fcm.builder()
            .message(memberFcmDto.message())
            .senderName(memberFcmDto.senderNickName())
            .isSend(false)
            .member(memberFcmDto.member())
            .fcmType(memberFcmDto.type())
            .build();
        fcmRepository.save(fcm);
    }

    @Transactional
    public void createFcm(Member opponent, String title, String message, FcmType fcmType) {
        if (duplicateFcm.checkFcm(opponent, title)) {
            MemberFcmDto dto = MemberFcmDto.builder()
                .senderNickName(title)
                .message(message)
                .member(opponent)
                .type(fcmType)
                .build();
            saveFcm(dto);
        }
    }

    private Fcm getFcm(Long fcmId) {
        return fcmRepository.findById(fcmId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_FCM));
    }

}