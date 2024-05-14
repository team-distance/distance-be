package io.festival.distance.domain.studentcard.service;


import static io.festival.distance.domain.firebase.entity.FcmType.STUDENT_CARD;
import static io.festival.distance.domain.firebase.service.FcmService.REJECT_STUDENT_CARD;
import static io.festival.distance.domain.firebase.service.FcmService.SET_SENDER_NAME;

import io.festival.distance.domain.firebase.service.FcmService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.studentcard.dto.AdminRequest;
import io.festival.distance.domain.studentcard.dto.ImageResponse;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.repository.StudentCardRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
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
    private final MemberReader memberReader;
    private final FcmService fcmService;
    @Transactional
    public void sendImage(MultipartFile file, String telNum) throws IOException {
        Member member = memberReader.findByTelNum(telNum);
        byte[] imageData = file.getBytes();
        StudentCard studentCard = StudentCard.builder()
            .member(member)
            .imageData(imageData)
            .build();
        studentCardRepository.save(studentCard);
        member.updateAuthUniv(UnivCert.SUCCESS);
    }

    @Transactional(readOnly = true)
    public List<ImageResponse> getImage() {
        return studentCardRepository.findAll()
            .stream()
            .map(ImageResponse::toEntity)
            .toList();
    }

    @Transactional
    public void approve(Long studentCardId) {
        StudentCard studentCard = getStudentCard(studentCardId);
        studentCardRepository.delete(studentCard);
    }

    @Transactional
    public void reject(Long studentCardId, AdminRequest adminRequest) {
        StudentCard studentCard = getStudentCard(studentCardId);
        Member member = studentCard.getMember();
        member.updateAuthUniv(UnivCert.valueOf(adminRequest.type()));
        fcmService.createFcm(member,SET_SENDER_NAME,REJECT_STUDENT_CARD, STUDENT_CARD);
        studentCardRepository.delete(studentCard);
    }

    private StudentCard getStudentCard(Long studentCardId) {
        return studentCardRepository.findById(studentCardId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_STUDENT_CARD));
    }
}
