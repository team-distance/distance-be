package io.festival.distance.domain.studentcard.service.serviceimpl;

import io.festival.distance.domain.studentcard.entity.StudentCard;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentCardUpdater {
    @Transactional
    public void update(StudentCard studentCard){
        studentCard.updateIsPass();
    }
}
