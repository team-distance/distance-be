package io.festival.distance.domain.studentcard.service;


import static io.festival.distance.domain.firebase.service.FcmService.SET_SENDER_NAME;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import io.festival.distance.domain.studentcard.dto.AdminRequest;
import io.festival.distance.domain.studentcard.dto.ImageResponse;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import io.festival.distance.domain.studentcard.service.serviceimpl.StudentCardCreator;
import io.festival.distance.domain.studentcard.service.serviceimpl.StudentCardDeleter;
import io.festival.distance.domain.studentcard.service.serviceimpl.StudentCardReader;
import io.festival.distance.domain.studentcard.service.serviceimpl.StudentCardUpdater;
import io.festival.distance.infra.sqs.SqsService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final MemberReader memberReader;
    private final MemberUpdater memberUpdater;
    private final StudentCardCreator studentCardCreator;
    private final StudentCardReader studentCardReader;
    private final StudentCardUpdater studentCardUpdater;
    private final StudentCardDeleter studentCardDeleter;
    private final SqsService sqsService;
    @Transactional
    public void sendImage(MultipartFile file, String telNum) throws IOException {
        Member member = memberReader.findTelNum(telNum);
        byte[] imageData = file.getBytes();
        StudentCard studentCard = studentCardCreator.getStudentCard(member, imageData);
        studentCardCreator.create(studentCard);
        memberUpdater.updateUniv(member, UnivCert.PENDING);
    }

    @Transactional(readOnly = true)
    public List<ImageResponse> getImage() {
        return studentCardReader.getImageList();
    }

    @Transactional
    public void approve(Long studentCardId) {
        StudentCard studentCard = studentCardReader.getStudentCard(studentCardId);
        Member member = memberReader.findTelNum(studentCard.getMember().getTelNum());
        memberUpdater.updateUniv(member,UnivCert.SUCCESS);
        studentCardUpdater.update(studentCard);
    }

    @Transactional
    public void reject(Long studentCardId, AdminRequest adminRequest) {
        StudentCard studentCard = studentCardReader.getStudentCard(studentCardId);
        Member member = studentCardReader.getMember(studentCard);
        memberUpdater.updateUniv(member, UnivCert.valueOf(adminRequest.type()));
        sqsService.sendMessage(
            member.getClientToken(),
            SET_SENDER_NAME,
            UnivCert.valueOf(adminRequest.type()).getMessage(),
            null
        );
    }

    public void removeStudentCard(Long studentCardId) {
        studentCardDeleter.delete(studentCardId);
    }
}
