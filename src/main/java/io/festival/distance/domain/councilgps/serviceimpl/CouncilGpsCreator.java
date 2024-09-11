package io.festival.distance.domain.councilgps.serviceimpl;

import io.festival.distance.domain.councilgps.dto.request.CouncilGpsRequest;
import io.festival.distance.domain.councilgps.entity.CouncilGps;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouncilGpsCreator {
    private final CouncilGpsSaver councilGpsSaver;

    public void create(List<CouncilGpsRequest> councilGpsRequestList, StudentCouncil studentCouncil){
        List<CouncilGps> councilGps = new ArrayList<>();
        for (CouncilGpsRequest councilGpsRequest : councilGpsRequestList) {
            CouncilGps gps = CouncilGps.builder()
                .councilLatitude(councilGpsRequest.councilLatitude())
                .councilLongitude(councilGpsRequest.councilLongitude())
                .location(councilGpsRequest.location())
                .studentCouncil(studentCouncil)
                .build();
            councilGps.add(gps);
        }
        councilGpsSaver.saveAll(councilGps);
    }
}
