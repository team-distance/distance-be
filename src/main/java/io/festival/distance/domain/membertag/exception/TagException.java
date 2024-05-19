package io.festival.distance.domain.membertag.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagException extends RuntimeException{
    TagErrorCode errorCode;
}
