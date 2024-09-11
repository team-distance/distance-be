package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import io.festival.distance.domain.councilgps.repository.CouncilGpsRepository;
import io.festival.distance.domain.councilimage.repository.CouncilImageRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.repository.CouncilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilDeleter {
    private final CouncilRepository councilRepository;
    private final CouncilImageRepository councilImageRepository;
    private final CouncilGpsRepository councilGpsRepository;

    @Transactional
    public void delete(StudentCouncil studentCouncil){
        councilRepository.delete(studentCouncil);
        councilImageRepository.deleteAllByStudentCouncil(studentCouncil);
        councilGpsRepository.deleteAllByStudentCouncil(studentCouncil);
    }
}
