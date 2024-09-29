package io.festival.distance.domain.studentcouncil.service;

import io.festival.distance.domain.councilgps.serviceimpl.CouncilGpsCreator;
import io.festival.distance.domain.councilimage.serviceimpl.CouncilImageCreator;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
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
import io.festival.distance.infra.redis.statistics.StatisticsRedisUpdater;
import io.festival.distance.infra.s3.config.S3PreSignedUrlProvider;
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
    private final StatisticsRedisUpdater statisticsRedisUpdater;
    private final S3PreSignedUrlProvider s3PreSignedUrlProvider;
    public List<S3UrlResponse> create(
        String telNum,
        ContentRequest contentRequest,
        List<String> fileRequest,
        List<Integer> priority
    ) {
        Member member = memberReader.findTelNum(telNum); //총학계정
        StudentCouncil studentCouncil = councilCreator.create(contentRequest, member);
        councilGpsCreator.create(contentRequest.councilGpsRequestList(), studentCouncil);
        //councilImageCreator.create(files, studentCouncil, priority);
        List<S3UrlResponse> s3UrlResponses =
            s3PreSignedUrlProvider.generatePreSignedUrl(fileRequest);
        councilImageCreator.create(s3UrlResponses,priority,studentCouncil);
        return s3UrlResponses;
    }

    public CouncilResponse findContents(String telNum, String school) {
        if (school == null) {
            Member member = memberReader.findTelNum(telNum);
            return councilReader.findList(member.getSchool());
        }
        return councilReader.findList(school);
    }

    public ContentResponse findContent(Long studentCouncilId, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        if (member.getAuthority().equals("ROLE_USER")) {
            statisticsRedisUpdater.update(studentCouncilId);
        }
        return councilReader.findOne(studentCouncilId);
    }

    public void deleteContent(Long studentCouncilId, String telNum) {
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
        List<MultipartFile> files,
        List<Integer> priority
    ) throws IOException, NoSuchAlgorithmException {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(studentCouncilId);
        councilUpdater.update(contentRequest, files, priority, studentCouncil);
    }
}
