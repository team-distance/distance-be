package io.festival.distance.domain.membertag.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagErrorCode {
    NOT_NULL_TAG(HttpStatusCode.BAD_REQUEST.getStatus(), "Tag를 입력하지 않았습니다!");
    private final int status;
    private final String message;
}
