package io.festival.distance.domain.eventmatching.controller;

import io.festival.distance.domain.eventmatching.dto.request.EventMatchRequest;
import io.festival.distance.domain.eventmatching.dto.response.EventMatchResponse;
import io.festival.distance.domain.eventmatching.service.EventMatchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event-matching")
@RequiredArgsConstructor
@CrossOrigin
public class EventMatchController {

    private final EventMatchService eventMatchService;

    /**
     * NOTE
     * 학교에 가입된 전체 멤버 조회
     */
    @GetMapping("/users")
    public ResponseEntity<List<EventMatchResponse>> getMemberList(
        @RequestParam(value = "school") String school
    ) {
        return ResponseEntity.ok(eventMatchService.findByMemberBySchool(school));
    }

    /**
     * NOTE
     * 유저를 매칭목록에 추가
     */
    @PostMapping
    public ResponseEntity<Void> registerEvent(@RequestBody EventMatchRequest eventMatchRequest){
        eventMatchService.createEvent(eventMatchRequest);
        return ResponseEntity.ok().build();
    }
}
