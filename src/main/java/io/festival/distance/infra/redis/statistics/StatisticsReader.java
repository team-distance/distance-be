package io.festival.distance.infra.redis.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsReader {

    private final StatisticsRedisRepository statisticsRedisRepository;
    private final StatisticsSaver statisticsSaver;
    private final StatisticsCreator statisticsCreator;

    public Statistics findStatistics(Long councilId) {
        return statisticsRedisRepository.findById(councilId)
            .orElseGet(() -> {
                Statistics statistics = statisticsCreator.create(councilId);
                return statisticsSaver.save(statistics);
            });
    }
}
