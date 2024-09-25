package io.festival.distance.domain.statistics.service.serviceimpl;

import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.infra.redis.statistics.StatisticsDeleter;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StatisticsUpdater {
    private final StatisticsRepository statisticsRepository;
    private final CouncilReader councilReader;
    private final StatisticsDeleter statisticsDeleter;

    @Transactional
    public void update(Long councilId, int count ){
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(councilId);
        CouncilStatistics statistics =
            statisticsRepository.findByDateAndStudentCouncil(LocalDate.now(),
                    studentCouncil)
                .orElseGet(() -> {
                    CouncilStatistics councilStatistics = CouncilStatistics.builder()
                        .count(1)
                        .date(LocalDate.now())
                        .studentCouncil(studentCouncil)
                        .role(studentCouncil.getAuthority())
                        .build();
                    statisticsRepository.save(councilStatistics);
                    return councilStatistics;
                });
        statistics.updateCount(count);
        statisticsDeleter.delete(councilId);
    }
}
