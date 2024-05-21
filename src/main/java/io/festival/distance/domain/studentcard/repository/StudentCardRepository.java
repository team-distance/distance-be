package io.festival.distance.domain.studentcard.repository;

import io.festival.distance.domain.studentcard.entity.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentCardRepository extends JpaRepository<StudentCard,Long> {

    @Query("delete from StudentCard s where s.isPass=true")
    void deleteAllStudentCard();
}
