package io.festival.distance.domain.report.repository;

import io.festival.distance.domain.report.entity.Report;
import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    boolean existsByMeAndOpponent(Member me,Member opponent);
}
