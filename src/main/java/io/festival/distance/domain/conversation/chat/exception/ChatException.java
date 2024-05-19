package io.festival.distance.domain.conversation.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatException extends RuntimeException{
    ChatErrorCode errorCode;
}
