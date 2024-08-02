package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.repository.CouncilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilSaver {
    private final CouncilRepository councilRepository;

    @Transactional
    public void save(StudentCouncil studentCouncil){
        councilRepository.save(studentCouncil);
    }
}
