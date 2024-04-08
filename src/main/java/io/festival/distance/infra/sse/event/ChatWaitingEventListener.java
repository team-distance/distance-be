package io.festival.distance.infra.sse.event;

import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatWaitingEventListener {
    private final SseService sseService;
    private final ChatWaitingService chatWaitingService;

    @EventListener
    public void onChatWaitingAdded(ChatWaitingAddedEvent event) {
        Long memberId = event.memberId();
        System.out.println("memberId = " + memberId);
        sseService.notify(memberId, chatWaitingService.countingWaitingRoom(memberId));
    }
}
