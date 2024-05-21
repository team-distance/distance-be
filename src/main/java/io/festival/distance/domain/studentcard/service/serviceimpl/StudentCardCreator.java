package io.festival.distance.domain.studentcard.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StudentCardCreator {
    private final StudentCardRepository studentCardRepository;

    public StudentCard getStudentCard(Member member, byte[] imageData){
        return StudentCard.builder()
            .member(member)
            .imageData(imageData)
            .isPass(false)
            .build();
    }

    @Transactional
    public void create(StudentCard studentCard){
        studentCardRepository.save(studentCard);
    }
}
