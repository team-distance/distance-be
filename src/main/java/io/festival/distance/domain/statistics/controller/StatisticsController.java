package io.festival.distance.domain.statistics.controller;

import io.festival.distance.domain.statistics.dto.response.BestStatisticsResponse;
import io.festival.distance.domain.statistics.dto.response.CountResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponses;
import io.festival.distance.domain.statistics.dto.response.StatisticsResultResponse;
import io.festival.distance.domain.statistics.service.StatisticsService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<StatisticsResultResponse> getDailyStatistics(
        @PathVariable Long councilId,
        @RequestParam(value = "type") String type,
        @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(value = "count") Integer count,
        Principal principal
        ) {
        return ResponseEntity.ok(
            statisticsService.checkStatistics(councilId, principal.getName(), type, date, count)
        );
    }

    /**
     * TODO
     * 전체 조회 수 조회
     */
    @GetMapping
    public ResponseEntity<StatisticsResponses> getTotalStatistics(
        @RequestParam(value = "type") String type,
        @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(value = "count") Integer count,
        Principal principal
    ) {
        return ResponseEntity.ok(
            statisticsService.checkTotalStatistics(type, date, count, principal.getName()));
    }

    /**
     * NOTE
     * 총학이 올린 게시글에 대한 전체 조회 수
     */
    @GetMapping("/total")
    public ResponseEntity<CountResponse> getTotalCount(Principal principal) {
        return ResponseEntity.ok(statisticsService.calculateTotalCount(principal.getName()));
    }

    @GetMapping("/total/{councilId}")
    public ResponseEntity<CountResponse> getTotalCountByCouncil(
        @PathVariable Long councilId,
        Principal principal
    ) {
        return ResponseEntity.ok(
            statisticsService.calculateTotalCountByCouncil(councilId)
        );
    }

    @GetMapping("/trending")
    public ResponseEntity<List<BestStatisticsResponse>> getBestCouncil(){
        return ResponseEntity.ok(statisticsService.findBestCouncil());
    }

}
