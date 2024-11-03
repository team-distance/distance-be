package io.festival.distance.domain.studentcard.service.serviceimpl;

import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StudentCardDeleter {
    private final StudentCardRepository studentCardRepository;
    @Scheduled(cron = "0 0 6 * * *") //매일 6시에 작동
    public void deleteIsTrue(){
        studentCardRepository.deleteAllStudentCard();
    }

    @Transactional
    public void delete(Long studentCardId){
        studentCardRepository.deleteById(studentCardId);
    }
}
