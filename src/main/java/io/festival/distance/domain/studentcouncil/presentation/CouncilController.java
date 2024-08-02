package io.festival.distance.domain.studentcouncil.presentation;

import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.dto.response.ContentResponse;
import io.festival.distance.domain.studentcouncil.service.CouncilService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> createContent(
        Principal principal,
        @RequestPart(value = "contentRequest") ContentRequest contentRequest,
        @RequestPart(value = "files",required = false) List<MultipartFile> files
    ) {
        councilService.create(
            principal.getName(),
            contentRequest,
            files);
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 총학 게시글 리스트 조회 API
     */
    @GetMapping
    public ResponseEntity<List<ContentResponse>> showContents(
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
    public ResponseEntity<ContentResponse> showContent(@PathVariable Long studentCouncilId) {
        return ResponseEntity.ok(councilService.findContent(studentCouncilId));
    }

   /* *//**
     * TODO
     * 수정 API(제목, 위치(선택적), 내용, 날짜(선택적), 이미지(선택적))
     *//*
    @PatchMapping("/{studentCouncilId}")
    public ResponseEntity<Long> modifyContent(
        @PathVariable Long studentCouncilId,
        @RequestPart(value = "contentRequest") ContentRequest contentRequest,
        @RequestPart(value = "files",required = false) List<MultipartFile> files,
        Principal principal
    ) {
        return ResponseEntity.ok(
            councilService.updateContent(
                studentCouncilId,
                principal.getName(),
                contentRequest,
                files
            )
        );
    }*/


    /**
     * NOTE
     * 총학 게시글 삭제 API
     */
    @DeleteMapping("/{studentCouncilId}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long studentCouncilId) {
        councilService.deleteContent(studentCouncilId);
        return ResponseEntity.ok().build();
    }
}
