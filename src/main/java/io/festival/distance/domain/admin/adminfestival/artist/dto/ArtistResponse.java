package io.festival.distance.domain.admin.adminfestival.artist.dto;

import io.festival.distance.domain.admin.adminfestival.artist.entity.Artist;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ArtistResponse(
    String artistName,
    LocalDateTime startAt,
    String artistImageUrl
) {
    public static ArtistResponse toEntity(Artist artist){
        return ArtistResponse.builder()
            .artistName(artist.getArtistName())
            .startAt(artist.getStartAt())
            .artistImageUrl(artist.getArtistImageUrl())
            .build();
    }
}
