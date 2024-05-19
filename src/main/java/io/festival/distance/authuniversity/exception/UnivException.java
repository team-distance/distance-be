package io.festival.distance.authuniversity.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnivException extends RuntimeException{
    UnivErrorCode errorCode;
}
