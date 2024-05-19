package io.festival.distance.domain.admin.adminfestival.artist.service;

import static io.festival.distance.domain.admin.adminfestival.artist.exception.ArtistErrorCode.NOT_EXIST_ARTIST;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistResponse;
import io.festival.distance.domain.admin.adminfestival.artist.entity.Artist;
import io.festival.distance.domain.admin.adminfestival.artist.exception.ArtistException;
import io.festival.distance.domain.admin.adminfestival.artist.repository.ArtistRepository;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3DeleteImage;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final S3DeleteImage s3DeleteImage;
    private final S3UploadImage s3UploadImage;

    @Transactional
    public void saveArtist(ArtistRequest artistRequest, MultipartFile file) {
        S3Response response = uploadArtistImage(file);
        Artist artist = getArtistEntity(
            artistRequest, response);
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
        String artistFileName = findArtistFileName(findArtist(artistId));
        deleteArtistImage(artistFileName);
        artistRepository.deleteById(artistId);
    }

    @Transactional
    public void modifyArtist(
        ArtistRequest artistRequest,
        MultipartFile file,
        Long artistId
    ) {
        Artist artist = findArtist(artistId);
        S3Response response = uploadArtistImage(file);
        updateArtistInfo(artistRequest, response, artist);
    }

    @Transactional(readOnly = true)
    public Artist findArtist(Long artistId) {
        return artistRepository.findById(artistId)
            .orElseThrow(() -> new ArtistException(NOT_EXIST_ARTIST));
    }

    public void deleteArtistImage(String artistFileName) {
        s3DeleteImage.deleteImage(artistFileName);
    }

    public String findArtistFileName(Artist artist) {
        return artist.getArtistFileName();
    }

    public S3Response uploadArtistImage(MultipartFile file) {
        return s3UploadImage.saveImage(file);
    }

    @Transactional
    public void updateArtistInfo(ArtistRequest artistRequest, S3Response s3Response,
        Artist artist) {
        artist.update(artistRequest, s3Response);
    }

    private static Artist getArtistEntity(ArtistRequest artistRequest, S3Response response) {
        return Artist.builder()
            .artistName(artistRequest.artistName())
            .startAt(artistRequest.startAt())
            .artistImageUrl(response.imageUrl())
            .school(artistRequest.school())
            .artistFileName(response.fileName())
            .build();
    }
}
