package io.festival.distance.domain.statistics.repository;

import io.festival.distance.domain.statistics.dto.response.BestStatisticsResponse;
import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<CouncilStatistics, Long> {

    @Query("select sum(c.count) "
        + "from CouncilStatistics c "
        + "where c.studentCouncil = :studentCouncil and c.date between :start and :end")
    Optional<Integer> findByStudentCouncilAndDateBetween(
        @Param(value = "studentCouncil") StudentCouncil studentCouncil,
        @Param(value = "start") LocalDate start,
        @Param(value = "end") LocalDate end
    );

    @Query("select sum(c.count) "
        + "from CouncilStatistics c "
        + "where c.date between :start and :end "
        + "and c.studentCouncil.authority = :authority")
    Optional<Integer> findByDateBetween(
        @Param(value = "start") LocalDate start,
        @Param(value = "end") LocalDate end,
        @Param(value = "authority") String authority
    );

    Optional<CouncilStatistics> findByDateAndStudentCouncil(
        LocalDate date,
        StudentCouncil studentCouncil
    );

    @Query("select sum (c.count) from CouncilStatistics c "
        + "where c.role = :role")
    Integer getByCount(@Param(value = "role") String role);

    @Query("select sum (c.count) from CouncilStatistics c where c.studentCouncil = :studentCouncil")
    Integer getByCountByStudentCouncil(
        @Param(value = "studentCouncil") StudentCouncil studentCouncil
    );

    @Query("SELECT c.studentCouncil.councilId AS councilId, c.studentCouncil.title AS title, SUM(c.count) AS count " +
        "FROM CouncilStatistics c " +
        "GROUP BY c.studentCouncil.councilId " +
        "ORDER BY SUM(c.count) DESC")
    List<BestStatisticsProjection> getBestStatisticsCouncil(Pageable pageable);
}
