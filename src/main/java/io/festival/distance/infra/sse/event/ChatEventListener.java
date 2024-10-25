package io.festival.distance.infra.sse.event;

import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.service.SseService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatEventListener {
    private final SseService sseService;
    private final ChatWaitingService chatWaitingService;
    private final ChatRoomService chatRoomService;
    @EventListener
    public void onChatWaitingAdded(ChatWaitingAddedEvent event) {
        Long memberId = event.memberId();
        sseService.notify(memberId, chatWaitingService.countingWaitingRoom(memberId));
    }

    @EventListener
    public void onChatMessageAdded(ChatMessageAddedEvent event) {
        Long memberId = event.memberId();
        sseService.messageNotify(memberId, chatRoomService.findAllRoomTest(memberId));
    }

    @EventListener
    public void onDeleteChatRoom(ChatRoomDeleteEvent event) {
        Long memberId = event.memberId();
        Long chatRoomId = event.chatRoomId();
        LocalDateTime createDt = event.createDt();
        sseService.messageNotify(memberId, chatRoomService.withdrawMessage(chatRoomId,createDt));
    }
}
