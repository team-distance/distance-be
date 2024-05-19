package io.festival.distance.domain.memberhobby.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HobbyErrorCode {
    NOT_NULL_HOBBY(HttpStatusCode.BAD_REQUEST.getStatus(), "Hobby를 입력하지 않았습니다!");
    private final int status;
    private final String message;
}
