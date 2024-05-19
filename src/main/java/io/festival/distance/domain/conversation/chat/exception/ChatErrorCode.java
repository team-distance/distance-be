package io.festival.distance.domain.conversation.chat.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatErrorCode {
    NOT_EXIST_MESSAGE(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 메시지입니다!"),
    TOO_MANY_MESSAGE_LENGTH(HttpStatusCode.LENGTH_REQUIRED.getStatus(), "메시지 길이가 너무 깁니다!"),
    NOT_EXIST_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 채팅방입니다!"),
    NOT_EXIST_WAITING_ROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 대기방입니다!"),
    TOO_MANY_MY_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 나의 방이 3개입니다!"),
    TOO_MANY_OPPONENT_CHATROOM(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 상대방의 방이 3개입니다!");
    private final int status;
    private final String message;
}
