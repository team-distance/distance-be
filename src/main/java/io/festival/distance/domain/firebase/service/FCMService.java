package io.festival.distance.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
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
        log.debug("Client 토큰: " + fcmDto.clientToken());
        // 알림 내용
        Message firebaseMessage = createNotificationContent(fcmDto);
        log.debug("fcm message>>>>" + firebaseMessage.toString());
        // 알림 전송
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(firebaseMessage);
            log.debug("fcm>>>> " + FirebaseMessaging.getInstance().sendAsync(firebaseMessage).get());
            //projects/distance-97455/messages/d10ae9f0-798d-4b0a-a02b-8188ed08b401
            log.debug("response>>>> " + response);
            //projects/distance-97455/messages/38d7c6f5-8009-45fe-9ad8-588e699585f2
        } catch (Exception e) {
            log.error("fcm error>> " + e.getMessage());
        }
        NotificationDto build = NotificationDto.builder()
            .FcmMessageId(response)
            .build();
        log.debug("Build>>> " + build);
        return build;
    }

    // 다른 곳에서도 재사용 가능하도록 분리함!

    /**
     * TODO
     * s3 링크 환경변수
     */
    private Message createNotificationContent(FcmDto fcmDto) {
        // 알림 내용
       /* return Message.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(WebpushNotification.builder()
                    .setTitle(fcmDto.senderNickName())
                    .setBody(fcmDto.message())
                    *//*.setImage(
                        "https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
                    .putCustomData("chatRoomId", String.valueOf(fcmDto.roomId()))*//*
                    .build())
                .build())
            .setToken(fcmDto.clientToken())
            .build();*/

            return Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                    .putData("nickName",fcmDto.senderNickName())
                    .putData("message",fcmDto.message())
                    .putData("chatRoomId", String.valueOf(fcmDto.roomId()))
                    .putData("iconLink","https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
                .build())
                .setToken(fcmDto.clientToken())
                .build();
    }

    //닉네임, 메시지 내용, chatroomid, icon link
}
