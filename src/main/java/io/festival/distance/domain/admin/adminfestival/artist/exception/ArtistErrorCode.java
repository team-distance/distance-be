package io.festival.distance.domain.admin.adminfestival.artist.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArtistErrorCode {
    NOT_EXIST_ARTIST(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 가수입니다!");
    private final int status;
    private final String message;
}
