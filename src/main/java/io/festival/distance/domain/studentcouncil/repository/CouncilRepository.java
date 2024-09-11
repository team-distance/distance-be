package io.festival.distance.domain.studentcouncil.repository;

import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouncilRepository extends JpaRepository<StudentCouncil,Long> {
    List<StudentCouncil> findAllBySchool(String school);
}
