package io.festival.distance.domain.conversation.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SenderType {
    USER("USER"),SYSTEM("SYSTEM");

    private final String senderType;
}
