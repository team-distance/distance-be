package io.festival.distance.domain.councilimage.repository;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilImageRepository extends JpaRepository<CouncilImage,Long> {
    List<CouncilImage> findAllByStudentCouncil(StudentCouncil studentCouncil);
    List<CouncilImage> findAllByStudentCouncilAndIsUsedTrue(StudentCouncil studentCouncil);
    void deleteAllByStudentCouncil(StudentCouncil studentCouncil);
    void deleteByFileName(String fileName);
}
