package io.festival.distance.domain.admin.adminfestival.artist.dto;

import io.festival.distance.domain.admin.adminfestival.artist.entity.Artist;
import lombok.Builder;

@Builder
public record ArtistResponse(
    String artistName,
    String startAt,
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
