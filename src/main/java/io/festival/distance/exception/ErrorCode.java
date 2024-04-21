package io.festival.distance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //NOT_EXIST_
    NOT_EXIST_ADMIN(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 관리자입니다!"),
    NOT_EXIST_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!"),
    NOT_EXIST_WAITING_ROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 대기방입니다!"),
    NOT_EXIST_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(),
        "Security Context에 인증 정보가 없습니다!"),
    NOT_EXIST_STUDENT_CARD(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 학생증입니다!"),
    NOT_EXIST_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 채팅방입니다!"),
    NOT_EXIST_GPS(HttpStatusCode.BAD_REQUEST.getStatus(),"사용자의 위치 정보가 존재하지 않습니다!"),
    NOT_EXIST_FCM(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 FCM 입니다!"),
    //EXIST_
    EXIST_EMAIL(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 Email 입니다!"),
    EXIST_ROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방과의 방이 존재합니다!"),
    EXIST_DECLARE(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방을 신고하였습니다!"),
    EXIST_WAITING_ROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 대기열입니다!"),
    EXIST_TEL_NUM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 번호 입니다!"),

    //NOT_NULL_
    NOT_NULL_NICKNAME(HttpStatusCode.BAD_REQUEST.getStatus(), "아무것도 입력하지 않았습니다!"),
    NOT_NULL_HOBBY(HttpStatusCode.BAD_REQUEST.getStatus(), "Hobby를 입력하지 않았습니다!"),
    NOT_NULL_TAG(HttpStatusCode.BAD_REQUEST.getStatus(), "Tag를 입력하지 않았습니다!"),

    // NOT_CORRECT_
    NOT_CORRECT_PASSWORD(HttpStatusCode.BAD_REQUEST.getStatus(), "비밀번호가 일치하지 않습니다!"),
    NOT_CORRECT_AUTHENTICATION_NUMBER(HttpStatusCode.FORBIDDEN.getStatus(), "인증번호가 일치하지 않습니다!"),

    //TOO_MANY
    TOO_MANY_MY_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 나의 방이 3개입니다!"),
    TOO_MANY_OPPONENT_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방의 방이 3개입니다!"),
    TOO_MANY_MESSAGE_LENGTH(HttpStatusCode.LENGTH_REQUIRED.getStatus(), "메시지 길이가 너무 깁니다!"),

    //JWT
    MALFORMED_JWT(HttpStatusCode.CONTAIN_BADWORD.getStatus(), "잘못된 JWT 서명입니다!"),
    EXPIRED_JWT(HttpStatusCode.UNAUTHORIZED.getStatus(), "만료된 JWT 토큰입니다!"),
    UNSUPPORTED_JWT(HttpStatusCode.CONTAIN_BADWORD.getStatus(), "지원되지 않는 JWT 토큰입니다!"),
    WRONG_JWT(HttpStatusCode.CONTAIN_BADWORD.getStatus(), "JWT 토큰이 잘못되었습니다!"),

    //etc,
    INVALID_EMAIL_FORMAT(HttpStatusCode.BAD_REQUEST.getStatus(), "이메일 형식이 올바르지 않습니다!"),

    THIRD_PARTY_AUTH_ERROR(HttpStatusCode.UNAUTHORIZED.getStatus(), "인증서가 유효하지 않습니다!"),

    NOT_AUTHENTICATION_STUDENT(HttpStatusCode.UNAUTHORIZED.getStatus(),"학생 인증이 되지 않았습니다!");

    private final int status;
    private final String message;
    }
