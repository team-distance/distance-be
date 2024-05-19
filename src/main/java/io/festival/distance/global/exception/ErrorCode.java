package io.festival.distance.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXPIRED_JWT(HttpStatusCode.UNAUTHORIZED.getStatus(), "만료된 JWT 토큰입니다!");

    private final int status;
    private final String message;
}
