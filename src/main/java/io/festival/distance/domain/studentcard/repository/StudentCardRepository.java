package io.festival.distance.domain.studentcard.repository;

import io.festival.distance.domain.studentcard.entity.StudentCard;
import java.beans.JavaBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCardRepository extends JpaRepository<StudentCard,Long> {

}
