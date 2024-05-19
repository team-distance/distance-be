package io.festival.distance.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomException extends RuntimeException{
    ErrorCode errorCode;
}
