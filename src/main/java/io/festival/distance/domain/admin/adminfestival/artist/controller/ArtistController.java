package io.festival.distance.domain.admin.adminfestival.artist.controller;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistResponse;
import io.festival.distance.domain.admin.adminfestival.artist.service.ArtistService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performance")
@CrossOrigin
public class ArtistController {

    private final ArtistService artistService;

    /** NOTE
     * 가수 이미지 등록 API
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "artistRequest") ArtistRequest artistRequest
    ) {
        artistService.saveArtist(artistRequest, file);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 가수 목록 불러오는 API
     */
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getArtist(@RequestParam String school) {
        return ResponseEntity.ok(artistService.getAllArtist(school));
    }

    /** NOTE
     * 가수 목록 삭제 API
     */
    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long artistId) {
        artistService.removeArtist(artistId);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 가수 정보 수정 API
     */
    @PatchMapping(value = "/{artistId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateArtist(
        @PathVariable Long artistId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "artistRequest") ArtistRequest artistRequest
    ) {
        artistService.modifyArtist(artistRequest, file, artistId);
        return ResponseEntity.ok().build();
    }
}