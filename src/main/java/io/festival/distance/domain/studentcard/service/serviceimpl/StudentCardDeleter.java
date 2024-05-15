package io.festival.distance.domain.studentcard.service.serviceimpl;

import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StudentCardDeleter {
    private final StudentCardRepository studentCardRepository;

    @Transactional
    public void delete(StudentCard studentCard){
        studentCardRepository.delete(studentCard);
    }
}
