package io.festival.distance.infra.redis.statistics;

import io.festival.distance.infra.redis.authenticate.AuthenticateNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsSaver {
    private final StatisticsRedisRepository statisticsRedisRepository;

    public Statistics save(Statistics statistics){
        return statisticsRedisRepository.save(statistics);
    }
}
