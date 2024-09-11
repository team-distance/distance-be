package io.festival.distance.domain.councilgps.repository;

import io.festival.distance.domain.councilgps.entity.CouncilGps;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilGpsRepository extends JpaRepository<CouncilGps,Long> {
    List<CouncilGps> findAllByStudentCouncil(StudentCouncil studentCouncil);
    void deleteAllByStudentCouncil(StudentCouncil studentCouncil);
}
