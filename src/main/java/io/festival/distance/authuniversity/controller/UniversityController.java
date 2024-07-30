package io.festival.distance.authuniversity.controller;

import com.sun.mail.iap.Response;
import io.festival.distance.authuniversity.dto.SchoolNameDto;
import io.festival.distance.authuniversity.dto.response.SchoolResponse;
import io.festival.distance.authuniversity.service.university.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/university")
public class UniversityController {
    private final UniversityService universityService;
    /**
     * NOTE
     * 대학 자동완성 API
     */
    @PostMapping
    public ResponseEntity< List<SchoolResponse>> getSchoolList(@RequestBody SchoolNameDto schoolNameDto){
        return ResponseEntity.ok(universityService.findSchools(schoolNameDto.schoolName()));
    }
}
