package io.festival.distance.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXPIRED_JWT(HttpStatusCode.UNAUTHORIZED.getStatus(), "만료된 JWT 토큰입니다!"),
    NOT_EXIST_STUDENT_CARD(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 학생증입니다!"),
    NOT_AUTHENTICATION_STUDENT(HttpStatusCode.UNAUTHORIZED.getStatus(), "학생 인증이 되지 않았습니다!"),

    FAILED_TO_IMAGE_UPLOAD(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE.getStatus(), "이미지 업로드에 실패했습니다!"),
    EXIST_DECLARE(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방을 신고하였습니다!"),
    NOT_NULL_TAG(HttpStatusCode.BAD_REQUEST.getStatus(), "Tag를 입력하지 않았습니다!"),
    NOT_NULL_HOBBY(HttpStatusCode.BAD_REQUEST.getStatus(), "Hobby를 입력하지 않았습니다!"),
    INVALID_EMAIL_FORMAT(HttpStatusCode.BAD_REQUEST.getStatus(), "이메일 형식이 올바르지 않습니다!"),
    EXIST_EMAIL(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 Email 입니다!"),
    NOT_CORRECT_PASSWORD(HttpStatusCode.BAD_REQUEST.getStatus(), "비밀번호가 일치하지 않습니다!"),
    EXIST_TEL_NUM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 번호 입니다!"),
    NOT_EXIST_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!"),
    NOT_EXIST_ROOM_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 방 멤버입니다!"),
    NOT_CORRECT_AUTHENTICATION_NUMBER(HttpStatusCode.FORBIDDEN.getStatus(), "인증번호가 일치하지 않습니다!"),
    NOT_EXIST_FCM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 FCM 입니다!"),
    THIRD_PARTY_AUTH_ERROR(HttpStatusCode.UNAUTHORIZED.getStatus(), "인증서가 유효하지 않습니다!"),
    NOT_EXIST_MESSAGE(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 메시지입니다!"),
    TOO_MANY_MESSAGE_LENGTH(HttpStatusCode.LENGTH_REQUIRED.getStatus(), "메시지 길이가 너무 깁니다!"),
    NOT_EXIST_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 채팅방입니다!"),
    NOT_EXIST_WAITING_ROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 대기방입니다!"),
    TOO_MANY_MY_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 나의 방이 5개입니다!"),
    TOO_MANY_OPPONENT_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방의 방이 5개입니다!"),
    NOT_EXIST_ARTIST(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 가수입니다!"),
    NOT_EXIST_FOOD_TRUCK(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 푸드트럭입니다!"),
    NOT_EXIST_MENU(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 메뉴입니다!"),
    NOT_EXIST_MEMBER_IP(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 IP입니다!"),
    TOO_MANY_REQUEST(HttpStatusCode.BAD_REQUEST.getStatus(),"하루 요청 수를 넘어갔습니다!"),
    NOT_EXIST_SCHOOL(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 대학교입니다!"),
    NOT_EXIST_CONTENT(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 게시글입니다!"),
    EXPIRATION_AUTHENTICATE_NUMBER(HttpStatusCode.GONE.getStatus(), "만료된 요청번호입니다!"),
    NOT_AUTHENTICATE_COUNCIL(HttpStatusCode.FORBIDDEN.getStatus(), "해당 학교 관리자가 아닙니다."),
    INVALID_TYPE(HttpStatusCode.BAD_REQUEST.getStatus(), "유효하지 않는 type입니다!"),
    ALREADY_EXIST_MEMBER(HttpStatusCode.CONFLICT.getStatus(),"이미 이벤트에 참여했습니다!"),
    ALREADY_EXIST_RECOMMEND(HttpStatusCode.CONFLICT.getStatus(), "이미 추천인을 등록했습니다!"),
    YET_ANSWER_BY_QUESTION(HttpStatusCode.BAD_REQUEST.getStatus(), "아직 질문에 대답을 하지 않았습니다"),
    NOT_REGISTER_SELF(HttpStatusCode.BAD_REQUEST.getStatus(), "자기자신을 추천할 수 없습니다!");
    private final int status;
    private final String message;
}
