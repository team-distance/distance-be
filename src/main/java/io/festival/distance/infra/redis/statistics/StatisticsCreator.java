package io.festival.distance.infra.redis.statistics;

import io.festival.distance.utils.TimeUtil;
import org.springframework.stereotype.Component;

@Component
public class StatisticsCreator {
    public Statistics create(Long councilId){
        return Statistics.builder()
            .id(councilId)
            .count(0)
            .expiration(TimeUtil.getSecondsUntilMidnight())
            .build();
    }
}
