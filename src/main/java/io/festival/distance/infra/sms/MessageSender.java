package io.festival.distance.infra.sms;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final Map<MessageType, MessageService> messageServiceMap;

    @Autowired
    public MessageSender(List<MessageService> messageServices) {
        this.messageServiceMap = messageServices.stream()
            .collect(Collectors.toMap(MessageService::getType, Function.identity()));
    }

    public MessageService getMessageService(String messageType) {
        if (!messageServiceMap.containsKey(MessageType.valueOf(messageType))) {
            throw new DistanceException(ErrorCode.INVALID_TYPE);
        }
        return messageServiceMap.get(MessageType.valueOf(messageType));
    }
}
