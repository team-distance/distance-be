package io.festival.distance.domain.member.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatusCode.BAD_REQUEST.getStatus(), "이메일 형식이 올바르지 않습니다!"),
    EXIST_EMAIL(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 Email 입니다!"),
    NOT_CORRECT_PASSWORD(HttpStatusCode.BAD_REQUEST.getStatus(), "비밀번호가 일치하지 않습니다!"),
    EXIST_TEL_NUM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 번호 입니다!"),
    NOT_EXIST_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!"),

    NOT_AUTHENTICATION_STUDENT(HttpStatusCode.UNAUTHORIZED.getStatus(), "학생 인증이 되지 않았습니다!"),
    NOT_CORRECT_AUTHENTICATION_NUMBER(HttpStatusCode.FORBIDDEN.getStatus(), "인증번호가 일치하지 않습니다!");

    private final int status;
    private final String message;
}
