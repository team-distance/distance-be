package io.festival.distance.infra.redis.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsDeleter {
    private final StatisticsRedisRepository statisticsRedisRepository;

    public void delete(Long councilId){
        statisticsRedisRepository.deleteById(councilId);
    }
}
