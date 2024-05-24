package io.festival.distance.infra.sse.event;

import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingCountDto;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.service.NotificationService;
import io.festival.distance.infra.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatWaitingEventListener {
    private final SseService sseService;
    private final ChatWaitingService chatWaitingService;
    private final NotificationService notificationService;
    @EventListener
    public void onChatWaitingAdded(ChatWaitingAddedEvent event) {
        Long memberId = event.memberId();
        ChatWaitingCountDto chatWaitingCountDto = chatWaitingService.countingWaitingRoom(memberId);
        sseService.notify(memberId, chatWaitingService.countingWaitingRoom(memberId));
        notificationService.sendNotification("/topic/waitingRoom",chatWaitingCountDto);
    }
}
