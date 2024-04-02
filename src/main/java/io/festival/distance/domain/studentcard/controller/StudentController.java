package io.festival.distance.domain.studentcard.controller;

import io.festival.distance.domain.studentcard.dto.ImageResponse;
import io.festival.distance.domain.studentcard.service.StudentService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studentcard")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendStudentCard(
        @RequestParam(name = "studentcard") MultipartFile multipartFile,
        Principal principal) throws IOException {
        studentService.sendImage(multipartFile, principal.getName());
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 사용자가 보낸 이미지들 관리자페이지에 보이기
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ImageResponse>> showImages(){
        return ResponseEntity.ok(studentService.getImage());
    }

    @DeleteMapping("/{studentCardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> acceptUniv(@PathVariable Long studentCardId){
        studentService.approve(studentCardId);
        return ResponseEntity.ok().build();
    }
}


