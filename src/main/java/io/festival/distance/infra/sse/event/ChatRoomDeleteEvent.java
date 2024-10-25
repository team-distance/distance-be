package io.festival.distance.infra.sse.event;

import java.time.LocalDateTime;

public record ChatRoomDeleteEvent(
    Long memberId,
    Long chatRoomId,
    LocalDateTime createDt
) {

}
