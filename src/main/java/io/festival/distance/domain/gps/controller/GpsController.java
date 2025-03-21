package io.festival.distance.domain.gps.controller;

import io.festival.distance.domain.gps.dto.DistanceResponse;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.gps.dto.GpsResponseDto;
import io.festival.distance.domain.gps.dto.MatchResponseDto;
import io.festival.distance.domain.gps.service.GpsService;
import io.festival.distance.domain.gps.service.serviceimpl.GpsProcessor;
import io.festival.distance.domain.studentcouncil.dto.response.SchoolLocation;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor //gpsService 생성자 자동 생성
@CrossOrigin //모든 외부 도메인의 요청을 허용한다.
public class GpsController {

    private final GpsService gpsService;
    private final GpsProcessor gpsProcessor;

    /**
     * NOTE
     * 유저 현재 위치 정보 갱신 API
     */
    @PostMapping("/update")
    public ResponseEntity<GpsResponseDto> updateGps(Principal principal,
        @RequestBody GpsDto gpsDto) {
        return ResponseEntity.ok(gpsService.updateMemberGps(principal.getName(), gpsDto));
    }

    /**
     * NOTE
     * 현재 유저 위치의 반경에 다른 사용자들의 위치가 속하는지 판단 API
     */
    @GetMapping("/matching")
    public ResponseEntity<MatchResponseDto> matching(
		Principal principal,
        @RequestParam("searchRange") double searchRange,
        @RequestParam("isPermitOtherSchool") boolean isPermitOtherSchool
	) {
        if (Objects.isNull(principal)) {
            return ResponseEntity.ok(gpsService.matchNonLoginUser());
        }
        return ResponseEntity.ok(
            gpsService.matchUser(principal.getName(),searchRange,isPermitOtherSchool)
        );
    }

    @GetMapping("/distance")
    public ResponseEntity<Double> distance(@RequestParam Long id1, @RequestParam Long id2) {
        return ResponseEntity.ok(gpsProcessor.getDistance(id1, id2));
    }

    @GetMapping("/distance/{chatRoomId}")
    public ResponseEntity<DistanceResponse> getDistance(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(gpsService.callDistance(chatRoomId));
    }

    @GetMapping
    public ResponseEntity<SchoolLocation> getMemberSchoolLocation(Principal principal){
        return ResponseEntity.ok(gpsService.getLocation(principal.getName()));
    }
}
