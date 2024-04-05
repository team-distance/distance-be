package io.festival.distance.domain.report.controller;

import io.festival.distance.domain.report.dto.ReportDto;
import io.festival.distance.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {
    private final ReportService declareService;

    @PostMapping
    public ResponseEntity<Void> sendDeclare(@RequestBody ReportDto reportDto, Principal principal){
        declareService.writeReport(reportDto,principal);
        return ResponseEntity.ok().build();
    }
}
