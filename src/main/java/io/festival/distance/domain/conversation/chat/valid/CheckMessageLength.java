package io.festival.distance.domain.conversation.chat.valid;


import static io.festival.distance.global.exception.ErrorCode.TOO_MANY_MESSAGE_LENGTH;

import io.festival.distance.global.exception.DistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckMessageLength {

    public void validMessageLength(String message) {
        if (message.length() > 500) {
            log.error("메시지 길이가 너무 깁니다");
            throw new DistanceException(TOO_MANY_MESSAGE_LENGTH);
        }
    }
}
