package io.festival.distance.domain.report.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.report.entity.Report;
import io.festival.distance.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReportCreator {
    private final ReportRepository reportRepository;

    @Transactional
    public void create(Report report){
        reportRepository.save(report);
    }

    public Report getReport(Member me, Member opponent, String content){
        return Report.builder()
            .reportContent(content)
            .me(me)
            .opponent(opponent)
            .build();
    }
}
