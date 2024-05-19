package io.festival.distance.domain.report.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode {
    //NOT_EXIST_
    EXIST_DECLARE(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방을 신고하였습니다!");
    private final int status;
    private final String message;
}
