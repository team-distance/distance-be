package io.festival.distance.domain.studentcard.service;


import io.festival.distance.domain.firebase.dto.FcmDto;
import io.festival.distance.domain.firebase.service.FCMService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.MemberService;
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
    private final MemberService memberService;
    private final FCMService fcmService;
    private final static String HOST_NAME = "[Distance]";

    @Transactional
    public void sendImage(MultipartFile file, String telNum) throws IOException {
        Member member = memberService.findByTelNum(telNum);
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
        sendFcm(studentCard, UnivCert.SUCCESS);
        studentCardRepository.delete(studentCard);
    }

    @Transactional
    public void reject(Long studentCardId, AdminRequest adminRequest) {
        StudentCard studentCard = getStudentCard(studentCardId);
        studentCard.getMember().updateAuthUniv(UnivCert.valueOf(adminRequest.type()));
        sendFcm(studentCard, UnivCert.valueOf(adminRequest.type()));
    }

    private void sendFcm(StudentCard studentCard, UnivCert univCert) {
        FcmDto fcmDto = FcmDto.builder()
            .senderNickName(HOST_NAME)
            .message(univCert.getMessage())
            .clientToken(studentCard.getMember().getClientToken())
            .build();
        fcmService.sendNotification(fcmDto);
    }

    private StudentCard getStudentCard(Long studentCardId) {
        return studentCardRepository.findById(studentCardId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_STUDENT_CARD));
    }
}
