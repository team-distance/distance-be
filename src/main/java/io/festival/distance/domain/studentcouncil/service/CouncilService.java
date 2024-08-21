package io.festival.distance.domain.studentcouncil.service;

import io.festival.distance.domain.councilgps.serviceimpl.CouncilGpsCreator;
import io.festival.distance.domain.councilimage.serviceimpl.CouncilImageCreator;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.dto.response.ContentResponse;
import io.festival.distance.domain.studentcouncil.dto.response.CouncilResponse;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilCreator;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilDeleter;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilUpdater;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilValidator;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CouncilService {

    private final MemberReader memberReader;
    private final CouncilGpsCreator councilGpsCreator;
    private final CouncilImageCreator councilImageCreator;
    private final CouncilCreator councilCreator;
    private final CouncilReader councilReader;
    private final CouncilDeleter councilDeleter;
    private final CouncilUpdater councilUpdater;
    private final CouncilValidator councilValidator;

    public void create(String telNum, ContentRequest contentRequest, List<MultipartFile> files) {
        Member member = memberReader.findTelNum(telNum); //총학계정
        StudentCouncil studentCouncil = councilCreator.create(contentRequest, member);
        councilGpsCreator.create(contentRequest.councilGpsRequestList(),studentCouncil);
        councilImageCreator.create(files,studentCouncil);
    }

    public CouncilResponse findContents(String telNum, String school) {
        if (school == null) {
            Member member = memberReader.findTelNum(telNum);
            return councilReader.findList(member.getSchool());
        }
        return councilReader.findList(school);
    }

    public ContentResponse findContent(Long studentCouncilId) {
        return councilReader.findOne(studentCouncilId);
    }

    public void deleteContent(Long studentCouncilId,String telNum) {
        Member member = memberReader.findTelNum(telNum);
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(studentCouncilId);
        councilValidator.isWriter(member, studentCouncil.getMember());
        councilDeleter.delete(studentCouncil);
    }

    // 기존이미지 전부 삭제 후 새롭게 등록
    public void updateContent(
        Long studentCouncilId,
        String telNum,
        ContentRequest contentRequest,
        List<MultipartFile> files
    ) throws IOException, NoSuchAlgorithmException {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(studentCouncilId);
        councilUpdater.update(contentRequest,files,studentCouncil);
    }
}
