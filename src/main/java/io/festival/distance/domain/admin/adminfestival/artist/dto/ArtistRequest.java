package io.festival.distance.domain.admin.adminfestival.artist.dto;

import java.time.LocalDateTime;

public record ArtistRequest(
    String artistName,
    LocalDateTime startAt,
    String school
) {

}
