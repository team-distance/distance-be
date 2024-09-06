package io.festival.distance.infra.scheduler;

import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.infra.redis.statistics.StatisticsDeleter;
import io.festival.distance.infra.redis.statistics.StatisticsReader;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StatisticsScheduler {

    private final StatisticsReader statisticsReader;
    private final StatisticsRepository statisticsRepository;
    private final CouncilReader councilReader;
    private final StatisticsDeleter statisticsDeleter;

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void schedulerByDate() {
        statisticsReader.findAll()
            .forEach(it -> {
                if(it == null) return;
                StudentCouncil studentCouncil = councilReader.findStudentCouncil(it.getId());
                Optional<CouncilStatistics> statistics =
                    statisticsRepository.findByDateAndStudentCouncil(LocalDate.now(),
                            studentCouncil)
                        .or(() -> {
                            CouncilStatistics councilStatistics = CouncilStatistics.builder()
                                .count(1)
                                .date(LocalDate.now())
                                .studentCouncil(studentCouncil)
                                .build();
                            statisticsRepository.save(councilStatistics);
                            return Optional.of(councilStatistics);
                        });
                statistics.ifPresent(stat -> stat.updateCount(it.getCount()));
                statisticsDeleter.delete(it.getId());
            });
    }
}
