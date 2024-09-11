package io.festival.distance.infra.scheduler;

import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsUpdater;
import io.festival.distance.infra.redis.statistics.StatisticsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StatisticsScheduler {

    private final StatisticsReader statisticsReader;
    private final StatisticsUpdater statisticsUpdater;
    @Transactional
    @Scheduled(fixedRate = 30000)
    public void schedulerByDate() {
        statisticsReader.findAll()
            .forEach(it -> {
                if(it == null) return;
                statisticsUpdater.update(it.getId(),it.getCount());
            });
    }
}
