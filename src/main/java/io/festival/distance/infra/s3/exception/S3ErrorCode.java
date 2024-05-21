package io.festival.distance.infra.s3.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3ErrorCode {
    FAILED_TO_IMAGE_UPLOAD(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE.getStatus(), "이미지 업로드에 실패했습니다!");

    private final int status;
    private final String message;
}
