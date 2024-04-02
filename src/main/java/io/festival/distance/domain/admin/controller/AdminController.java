package io.festival.distance.domain.admin.controller;

import io.festival.distance.domain.admin.dto.AdminSignUpDto;
import io.festival.distance.domain.admin.dto.PageRequestDto;
import io.festival.distance.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    /** NOTE
     * 관리자 회원기입
     * @param adminSignUpDto 가입에 필요한 값
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody AdminSignUpDto adminSignUpDto){
        return ResponseEntity.ok(adminService.createAdmin(adminSignUpDto));
    }

    public static PageRequest pageGenerate(PageRequestDto dto) {
        int page=dto.page();
        int size=dto.size();
        return PageRequest.of(page, size);
    }
}
