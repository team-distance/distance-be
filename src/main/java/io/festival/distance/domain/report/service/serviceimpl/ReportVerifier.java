package io.festival.distance.domain.report.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportVerifier {
    private final ReportRepository reportRepository;

    public boolean verifyReport(Member me, Member opponent){
        return reportRepository.existsByMeAndOpponent(me,opponent);
    }
}
