package io.festival.distance.domain.firebase.service.serviceimpl;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import io.festival.distance.domain.firebase.entity.Fcm;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmSender {
    private final FcmReader fcmReader;
    private final FcmUpdater fcmUpdater;
    public void sendSystemNotification(Message message, Long fcmId) {
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("Success to send message");
            Fcm fcm = fcmReader.getFcm(fcmId);
            fcmUpdater.update(fcm);
        } catch (Exception e) {
            log.error("Failed to send message");
        }
    }

    public BatchResponse sendNotification(MulticastMessage multicastMessage)
        throws ExecutionException, InterruptedException {
        return FirebaseMessaging.getInstance()
            .sendEachForMulticastAsync(multicastMessage)
            .get();
    }


}
