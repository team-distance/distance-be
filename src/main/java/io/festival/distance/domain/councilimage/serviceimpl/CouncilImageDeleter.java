package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.repository.CouncilImageRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilImageDeleter {
    private final CouncilImageRepository councilImageRepository;

    @Transactional
    public void delete(StudentCouncil studentCouncil){
        councilImageRepository.deleteAllByStudentCouncil(studentCouncil);
    }
}
