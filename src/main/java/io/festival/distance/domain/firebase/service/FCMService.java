package io.festival.distance.domain.firebase.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import io.festival.distance.domain.firebase.dto.FcmDto;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.firebase.repository.FcmRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private final FcmRepository fcmRepository;
    public static final String ADD_WAITING_ROOM_MESSAGE="새로운 채팅 요청이 들어왔습니다!";
    public static final String REJECT_STUDENT_CARD="학생증 인증에 실패하였습니다!";
    public static final String SET_SENDER_NAME="[관리자]";
    public void sendNotification(FcmDto fcmDto) {
        log.info("Client 토큰: " + fcmDto.clientToken());
        // 알림 내용
        Message firebaseMessage = createNotificationContent(fcmDto);
        // 알림 전송
        String response = null;

        try {
            response = FirebaseMessaging.getInstance().sendAsync(firebaseMessage).get();
            log.info("response>>>> " + response);

        } catch (Exception e) {
            log.error("fcm error>> " + e.getMessage());
        }
    }

    /**
     * TODO
     * s3 링크 환경변수
     */
    private Message createNotificationContent(FcmDto fcmDto) {
        // 알림 내용
        return Message.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(WebpushNotification.builder()
                    .setTitle(fcmDto.senderNickName())
                    .setBody(fcmDto.message())
                    .setImage(
                        "https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
                    .build())
                .build())
            .setToken(fcmDto.clientToken())
            .build();
    }


    @Transactional
    public void saveFcm(MemberFcmDto memberFcmDto) {
        Fcm fcm = Fcm.builder()
            .message(memberFcmDto.message())
            .isSend(false)
            .member(memberFcmDto.member())
            .build();
        fcmRepository.save(fcm);
    }


    /**
     * 채팅 대기열 알림
     */
    @Transactional
    @Scheduled(fixedRate = 10000)
    public void sendSystemNotification() {
        log.info("scheduled 실행!!");
        sendNotificationForMessage(ADD_WAITING_ROOM_MESSAGE);
        sendNotificationForMessage(REJECT_STUDENT_CARD);
    }

    private void sendNotificationForMessage(String message) {
        List<MemberFcmDto> fcmDtoList = fcmRepository.findAllByIsSend(message)
            .stream()
            .map(MemberFcmDto::fromEntity)
            .toList();

        if (!fcmDtoList.isEmpty()) {
            // 알림 내용
            MulticastMessage firebaseMessage = createSystemNotification(fcmDtoList);
            // 알림 전송
            BatchResponse response = null;

            try {
                response = FirebaseMessaging.getInstance()
                    .sendEachForMulticastAsync(firebaseMessage)
                    .get();
                log.info("response>>>> " + response);

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
}
