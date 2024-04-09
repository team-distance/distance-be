package io.festival.distance.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.festival.distance.domain.firebase.dto.FcmDto;
import io.festival.distance.domain.firebase.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    public NotificationDto sendNotification(FcmDto fcmDto) {
        log.info("Client 토큰: " + fcmDto.clientToken());
        // 알림 내용
        Message firebaseMessage = createNotificationContent(fcmDto);
        // 알림 전송
        String response;
        try {
            response = FirebaseMessaging.getInstance().send(firebaseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            response = "알림 전송 실패";
        }
       return NotificationDto.builder()
            .FcmMessageId(response)
            .build();
    }

    // 다른 곳에서도 재사용 가능하도록 분리함!
    private Message createNotificationContent(FcmDto fcmDto) {
        // 알림 내용
        return Message.builder()
            .setToken(fcmDto.clientToken())
            .setNotification(Notification.builder()
                .setTitle(fcmDto.senderNickName())
                .setBody(fcmDto.message())
                .build())
            .build();
    }
}
