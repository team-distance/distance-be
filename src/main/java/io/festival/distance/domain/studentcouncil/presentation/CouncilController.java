package io.festival.distance.domain.studentcouncil.presentation;

import io.festival.distance.domain.image.dto.request.FileListRequest;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.dto.response.ContentResponse;
import io.festival.distance.domain.studentcouncil.dto.response.CouncilResponse;
import io.festival.distance.domain.studentcouncil.service.CouncilService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/council")
public class CouncilController {

    private final CouncilService councilService;


    /**
     * NOTE
     * 총학 게시글 작성 API
     */
    @PostMapping
    public ResponseEntity<List<S3UrlResponse>> createContent(
        Principal principal,
        @RequestBody ContentRequest contentRequest,
        @RequestBody FileListRequest fileListRequest,
        //@RequestPart(value = "files", required = false) List<MultipartFile> files,
        @RequestBody List<Integer> priority
    ) {
        return ResponseEntity.ok(
            councilService.create(
            principal.getName(),
            contentRequest,
            fileListRequest.toStringList(),
            priority
            )
        );
    }

    /**
     * NOTE
     * 총학 게시글 리스트 조회 API
     */
    @GetMapping
    public ResponseEntity<CouncilResponse> showContents(
        @RequestParam(value = "school", required = false) String school,
        Principal principal
    ) {
        return ResponseEntity.ok(councilService.findContents(principal.getName(), school));
    }

    /**
     * NOTE
     * 총학 게시글 세부조회 API
     */
    @GetMapping("/{studentCouncilId}")
    public ResponseEntity<ContentResponse> showContent(
        @PathVariable Long studentCouncilId,
        Principal principal
    ) {
        return ResponseEntity.ok(councilService.findContent(studentCouncilId, principal.getName()));
    }

    /**
     * TODO
     * 수정 API(제목, 위치(선택적), 내용, 날짜(선택적), 이미지(선택적))
     */
    @PatchMapping("/{studentCouncilId}")
    public ResponseEntity<Void> modifyContent(
        @PathVariable Long studentCouncilId,
        @RequestPart(value = "contentRequest") ContentRequest contentRequest,
        @RequestPart(value = "files", required = false) List<MultipartFile> files,
        @RequestPart(value = "priority") List<Integer> priority,
        Principal principal
    ) throws IOException, NoSuchAlgorithmException {
        councilService.updateContent(
            studentCouncilId,
            principal.getName(),
            contentRequest,
            files,
            priority
        );
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 총학 게시글 삭제 API
     */
    @DeleteMapping("/{studentCouncilId}")
    public ResponseEntity<Void> deleteContent(
        @PathVariable Long studentCouncilId,
        Principal principal
    ) {
        councilService.deleteContent(studentCouncilId, principal.getName());
        return ResponseEntity.ok().build();
    }
}
