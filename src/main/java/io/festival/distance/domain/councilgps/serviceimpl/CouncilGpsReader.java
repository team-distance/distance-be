package io.festival.distance.domain.councilgps.serviceimpl;

import io.festival.distance.domain.councilgps.dto.response.CouncilGpsResponse;
import io.festival.distance.domain.councilgps.entity.CouncilGps;
import io.festival.distance.domain.councilgps.repository.CouncilGpsRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilGpsReader {
    private final CouncilGpsRepository councilGpsRepository;

    @Transactional(readOnly = true)
    public List<CouncilGpsResponse> findGpsList(StudentCouncil studentCouncil){
        return councilGpsRepository.findAllByStudentCouncil(studentCouncil)
            .stream()
            .map(CouncilGpsResponse::toCouncilGpsResponse)
            .toList();
    }

    public List<CouncilGps> findGpsEntity(StudentCouncil studentCouncil){
        return councilGpsRepository.findAllByStudentCouncil(studentCouncil);
    }
}
