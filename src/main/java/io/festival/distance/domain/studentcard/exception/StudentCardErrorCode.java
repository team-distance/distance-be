package io.festival.distance.domain.studentcard.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudentCardErrorCode {
    //NOT_EXIST_
    NOT_EXIST_STUDENT_CARD(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 학생증입니다!"),
    NOT_AUTHENTICATION_STUDENT(HttpStatusCode.UNAUTHORIZED.getStatus(), "학생 인증이 되지 않았습니다!"),
    FAILED_TO_IMAGE_UPLOAD(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE.getStatus(), "이미지 업로드에 실패했습니다!");
    private final int status;
    private final String message;
}
