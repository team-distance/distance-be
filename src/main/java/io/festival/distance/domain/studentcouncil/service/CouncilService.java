package io.festival.distance.domain.studentcouncil.service;

import io.festival.distance.domain.councilgps.serviceimpl.CouncilGpsCreator;
import io.festival.distance.domain.councilimage.serviceimpl.CouncilImageCreator;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.dto.response.ContentResponse;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilCreator;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilDeleter;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilUpdater;
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

    public void create(String telNum, ContentRequest contentRequest, List<MultipartFile> files) {
        Member member = memberReader.findTelNum(telNum); //총학계정
        councilCreator.create(contentRequest, member);
        councilGpsCreator.create(contentRequest.councilGpsRequestList());
        councilImageCreator.create(files);
    }

    public List<ContentResponse> findContents(String telNum, String school) {
        if (school == null) {
            Member member = memberReader.findTelNum(telNum);
            return councilReader.findList(member.getSchool());
        }
        return councilReader.findList(school);
    }

    public ContentResponse findContent(Long studentCouncilId) {
        return councilReader.findOne(studentCouncilId);
    }

    public void deleteContent(Long studentCouncilId) {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(studentCouncilId);
        councilDeleter.delete(studentCouncil);
    }

    public void updateContent(
        Long studentCouncilId,
        String telNum,
        ContentRequest contentRequest,
        List<MultipartFile> files
    ) {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(studentCouncilId);
        councilUpdater.update(contentRequest,files,studentCouncil);
    }
}
