package io.festival.distance.infra.sse.service;

import com.sun.nio.sctp.Notification;
import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(String destination,ChatWaitingCountDto countDto) {
        simpMessagingTemplate.convertAndSend(destination, countDto);
    }
}
