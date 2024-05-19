package io.festival.distance.authuniversity.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnivErrorCode {
    NOT_EXIST_SCHOOL(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 대학교입니다!");

    private final int status;
    private final String message;
}
