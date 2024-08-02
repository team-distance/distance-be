package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.dto.response.CouncilImageResponse;
import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.councilimage.repository.CouncilImageRepository;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilImageReader {
    private final CouncilImageRepository councilImageRepository;
    @Transactional(readOnly = true)
    public List<CouncilImageResponse> findImageList(StudentCouncil studentCouncil){
        return councilImageRepository.findAllByStudentCouncil(studentCouncil)
            .stream()
            .map(CouncilImageResponse::toCouncilImageResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<CouncilImage> findImageEntity(StudentCouncil studentCouncil){
        return councilImageRepository.findAllByStudentCouncil(studentCouncil);
    }
}
