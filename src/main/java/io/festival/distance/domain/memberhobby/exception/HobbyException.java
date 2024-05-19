package io.festival.distance.domain.memberhobby.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HobbyException extends RuntimeException{
    HobbyErrorCode errorCode;
}
