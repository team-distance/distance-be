package io.festival.distance.infra.redis.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsRedisUpdater {
    private final StatisticsReader statisticsReader;
    private final StatisticsSaver statisticsSaver;
    public void update(Long councilId){
        Statistics statistics = statisticsReader.findStatistics(councilId);
        statistics.update();
        statisticsSaver.save(statistics);
    }
}