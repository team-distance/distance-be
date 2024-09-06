package io.festival.distance.domain.statistics.controller;

import io.festival.distance.domain.statistics.dto.response.CountResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponse;
import io.festival.distance.domain.statistics.service.StatisticsService;
import java.security.Principal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.ResponseServer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * TODO
     * 게시글 별 일간 조회 수 조회
     */
    @GetMapping("/{councilId}")
    public ResponseEntity<StatisticsResponse> getDailyStatistics(
        @PathVariable Long councilId,
        @RequestParam(value = "type") String type,
        @RequestParam(value ="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        Principal principal
    ) {
        return ResponseEntity.ok(
            statisticsService.checkStatistics(councilId, principal.getName(),type,date)
        );
    }

    /**
     * TODO
     * 전체 조회 수 조회
     */
    @GetMapping
    public ResponseEntity<StatisticsResponse> getTotalStatistics(
        @RequestParam(value = "type") String type,
        @RequestParam(value ="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        Principal principal
    ){
        return ResponseEntity.ok(statisticsService.checkTotalStatistics(type,date,principal.getName()));
    }

    @GetMapping("/total")
    public ResponseEntity<CountResponse> getTotalCount(Principal principal){
        return ResponseEntity.ok(statisticsService.calculateTotalCount(principal.getName()));
    }
}
