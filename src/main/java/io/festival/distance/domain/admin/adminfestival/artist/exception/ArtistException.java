package io.festival.distance.domain.admin.adminfestival.artist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArtistException extends RuntimeException{
    ArtistErrorCode errorCode;
}
