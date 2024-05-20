package io.festival.distance.domain.studentcard.service.serviceimpl;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_STUDENT_CARD;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcard.dto.ImageResponse;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import io.festival.distance.global.exception.DistanceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StudentCardReader {

    private final StudentCardRepository studentCardRepository;

    @Transactional(readOnly = true)
    public List<StudentCard> getStudentCardList() {
        return studentCardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ImageResponse> getImageList() {
        return getStudentCardList().stream()
            .map(ImageResponse::toEntity)
            .toList();
    }

    public StudentCard getStudentCard(Long studentCardId) {
        return studentCardRepository.findById(studentCardId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_STUDENT_CARD));
    }

    public Member getMember(StudentCard studentCard) {
        return studentCard.getMember();
    }
}
