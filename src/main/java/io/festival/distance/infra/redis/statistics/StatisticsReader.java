package io.festival.distance.infra.redis.statistics;

import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StatisticsReader {

    private final StatisticsRedisRepository statisticsRedisRepository;
    private final StatisticsSaver statisticsSaver;
    private final StatisticsCreator statisticsCreator;
    private final StatisticsRepository statisticsRepository;

    @Transactional(readOnly = true)
    public Statistics findStatistics(Long councilId) {
        return statisticsRedisRepository.findById(councilId)
            .orElseGet(() -> {
                Statistics statistics = statisticsCreator.create(councilId);
                return statisticsSaver.save(statistics);
            });
    }

    @Transactional(readOnly = true)
    public Integer findTotalCount(String role){
        return statisticsRepository.getByCount(role);
    }
}
