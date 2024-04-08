package io.festival.distance.infra.sse.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@RequiredArgsConstructor
public class SseRepository {

    // 모든 Emitters를 저장하는 ConcurrentHashMap, 동시성 이슈 해결
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Long memberId, SseEmitter emitter) {
        emitters.put(memberId, emitter);
    }

    public void deleteById(Long memberId) {
        emitters.remove(memberId);
    }

    public SseEmitter get(Long memberId) {
        return emitters.get(memberId);
    }
}
