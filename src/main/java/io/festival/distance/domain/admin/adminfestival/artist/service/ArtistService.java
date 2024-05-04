package io.festival.distance.domain.admin.adminfestival.artist.service;

import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.artist.entity.Artist;
import io.festival.distance.domain.admin.adminfestival.artist.repository.ArtistRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public void saveArtist(ArtistRequest artistRequest, S3Response response) {
        Artist artist = Artist.builder()
            .artistName(artistRequest.artistName())
            .startAt(artistRequest.startAt())
            .artistImageUrl(response.imageUrl())
            .school(artistRequest.school())
            .artistFileName(response.fileName())
            .build();
        artistRepository.save(artist);
    }

    @Transactional(readOnly = true)
    public List<ArtistResponse> getAllArtist(String school) {
        return artistRepository.findAllBySchool(school)
            .stream()
            .map(ArtistResponse::toEntity)
            .toList();
    }

    @Transactional
    public void removeArtist(Long artistId) {
        artistRepository.deleteById(artistId);
    }

    @Transactional
    public void modifyArtist(
        ArtistRequest artistRequest,
        S3Response response,
        Long artistId
    ) {
        Artist artist = findArtist(artistId);
        artist.update(artistRequest,response);
    }

    @Transactional(readOnly = true)
    public Artist findArtist(Long artistId) {
        return artistRepository.findById(artistId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }
}
