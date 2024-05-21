package io.festival.distance.domain.firebase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FcmException extends RuntimeException{
    FcmErrorCode errorCode;
}
