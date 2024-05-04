package io.festival.distance.domain.admin.adminfestival.artist.controller;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistSearch;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistResponse;
import io.festival.distance.domain.admin.adminfestival.artist.service.ArtistService;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performance")
@CrossOrigin
public class ArtistController {

    private final ArtistService artistService;
    private final S3UploadImage s3UploadImage;

    /**
     * NOTE
     * 가수 이미지 등록 API
     *
     * @param file 이미지 파일
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> uploadFile(
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "artistRequest") ArtistRequest artistRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file); //s3에 이미지 저장
        artistService.saveArtist(artistRequest, response);
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 가수 목록 불러오는 API
     *
     * @param artistSearch
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getArtist(@RequestBody ArtistSearch artistSearch) {
        return ResponseEntity.ok(artistService.getAllArtist(artistSearch.school()));
    }


    /**
     * NOTE
     * 가수 목록 삭제 API
     *
     * @param artistId
     * @return
     */
    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long artistId) {
        String artistFileName = artistService.findArtist(artistId).getArtistFileName();
        s3UploadImage.deleteImage(artistFileName);
        artistService.removeArtist(artistId);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 가수 정보 수정 API
     * @param file
     * @param artistRequest
     * @return
     */
    @PatchMapping(value = "/{artistId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateArtist(
        @PathVariable Long artistId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "artistRequest") ArtistRequest artistRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file);
        artistService.modifyArtist(artistRequest,response,artistId);
        return ResponseEntity.ok().build();
    }
}
