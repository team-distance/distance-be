package io.festival.distance.infra.sse.service;

import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.infra.sse.repository.SseRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class SseService {

    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000;

    private final SseRepository sseRepository;
    private final ChatWaitingService chatWaitingService;
    private final ChatRoomService chatRoomService;

    /**
     * 클라이언트가 구독을 위해 호출하는 메서드.
     *
     * @param memberId - 구독하는 클라이언트의 사용자 아이디.
     * @return SseEmitter - 서버에서 보낸 이벤트 Emitter
     */
    public SseEmitter subscribe(Long memberId) {
        SseEmitter emitter = createEmitter(memberId);
        sendToClient(memberId,chatRoomService.findAllRoomTest(memberId),"chatRoom");
        sendToClient(memberId,chatWaitingService.countingWaitingRoom(memberId),"waitingCount");
        return emitter;
    }

    /**
     * 서버의 이벤트를 클라이언트에게 보내는 메서드 다른 서비스 로직에서 이 메서드를 사용해 데이터를 Object event에 넣고 전송하면 된다.
     *
     * @param memberId - 메세지를 전송할 사용자의 아이디.
     * @param event  - 전송할 이벤트 객체.
     */
    public void notify(Long memberId, Object event) {
        sendToClient(memberId, event,"waitingCount");
    }

    public void messageNotify(Long memberId, Object event){
        sendToClient(memberId,event,"chatRoom");
    }
    /**
     * 클라이언트에게 데이터를 전송
     *
     * @param memberId   - 데이터를 받을 사용자의 아이디.
     * @param data - 전송할 데이터.
     */
    private void sendToClient(Long memberId, Object data,String eventName) {
        SseEmitter emitter = sseRepository.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException exception) {
                sseRepository.deleteById(memberId);
                //emitter.completeWithError(exception);
            }
        }
    }

    /**
     * 사용자 아이디를 기반으로 이벤트 Emitter를 생성
     *
     * @param memberId - 사용자 아이디.
     * @return SseEmitter - 생성된 이벤트 Emitter.
     */
    private SseEmitter createEmitter(Long memberId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        sseRepository.save(memberId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> sseRepository.deleteById(memberId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> sseRepository.deleteById(memberId));

        return emitter;
    }
}
