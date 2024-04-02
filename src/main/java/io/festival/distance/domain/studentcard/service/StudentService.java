package io.festival.distance.domain.studentcard.service;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.studentcard.dto.ImageResponse;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentCardRepository studentCardRepository;
    private final MemberService memberService;

    public void sendImage(MultipartFile file, String telNum) throws IOException {
        Member member = memberService.findByTelNum(telNum);
        byte[] imageData = file.getBytes();
        StudentCard studentCard = StudentCard.builder()
            .member(member)
            .imageData(imageData)
            .build();
        studentCardRepository.save(studentCard);
    }

    @Transactional(readOnly = true)
    public List<ImageResponse> getImage() {
        return studentCardRepository.findAll()
            .stream()
            .map(ImageResponse::toEntity)
            .toList();
    }

    public void approve(Long studentCardId) {
        StudentCard studentCard = studentCardRepository.findById(studentCardId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 학생증입니다"));

        Member member = studentCard.getMember();
        member.updateAuthUniv();
        studentCardRepository.delete(studentCard);
    }
}
