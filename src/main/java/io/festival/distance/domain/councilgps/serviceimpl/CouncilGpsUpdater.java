package io.festival.distance.domain.councilgps.serviceimpl;

import io.festival.distance.domain.councilgps.dto.request.CouncilGpsRequest;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouncilGpsUpdater {
    private final CouncilGpsReader councilGpsReader;
    public void update(List<CouncilGpsRequest> councilGpsRequest, StudentCouncil studentCouncil){
        councilGpsReader.findGpsEntity(studentCouncil)
            .forEach(councilGps -> {
                councilGpsRequest
                    .forEach(councilGps::updateGps);
            });
    }
}
