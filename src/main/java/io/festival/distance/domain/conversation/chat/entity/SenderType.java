package io.festival.distance.domain.conversation.chat.entity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SenderType {
    USER("USER"),
    SYSTEM("SYSTEM"),
    CALL_REQUEST("CALL_REQUEST"),
    CALL_RESPONSE("CALL_RESPONSE"),
    IMAGE("IMAGE"),
    COME("COME");

    private final String senderType;

    private static final Map<String, SenderType> SENDER_TYPE_MAP = Collections.unmodifiableMap(
        Stream.of(values()).collect(Collectors.toMap(SenderType::getSenderType, Function.identity())
        ));

    public static SenderType of(final String senderType){
        return SenderType.valueOf(String.valueOf(SENDER_TYPE_MAP.get(senderType)));
    }
}
