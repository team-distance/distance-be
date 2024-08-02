package io.festival.distance.domain.councilgps.serviceimpl;

import io.festival.distance.domain.councilgps.entity.CouncilGps;
import io.festival.distance.domain.councilgps.repository.CouncilGpsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilGpsSaver {
    private final CouncilGpsRepository councilGpsRepository;

    @Transactional
    public void saveAll(List<CouncilGps> councilGps){
        councilGpsRepository.saveAll(councilGps);
    }
}
