package io.festival.distance.domain.firebase.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmErrorCode {
    NOT_EXIST_FCM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 FCM 입니다!"),
    THIRD_PARTY_AUTH_ERROR(HttpStatusCode.UNAUTHORIZED.getStatus(), "인증서가 유효하지 않습니다!");
    private final int status;
    private final String message;
}
