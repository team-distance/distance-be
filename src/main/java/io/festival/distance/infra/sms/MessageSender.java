package io.festival.distance.infra.sms;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final Map<String, MessageService> messageServiceMap;

    public MessageSender(
        SignUpMessageService signUpMessageService,
        FindMessageService findMessageService
    ) {
        this.messageServiceMap = Map.of(
            "SIGNUP", signUpMessageService,
            "FIND", findMessageService
        );
    }

    public MessageService getMessageService(String messageType) {
        return messageServiceMap.getOrDefault(
            messageType, (telNum, verificationCode) -> {
                throw new DistanceException(ErrorCode.INVALID_TYPE);
            }
        );
    }
}
