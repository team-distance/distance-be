package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import static io.festival.distance.authuniversity.domain.University.getSchoolLocation;

import io.festival.distance.domain.councilgps.dto.response.CouncilGpsResponse;
import io.festival.distance.domain.councilgps.serviceimpl.CouncilGpsReader;
import io.festival.distance.domain.councilimage.dto.response.CouncilImageResponse;
import io.festival.distance.domain.councilimage.serviceimpl.CouncilImageReader;
import io.festival.distance.domain.studentcouncil.dto.response.ContentResponse;
import io.festival.distance.domain.studentcouncil.dto.response.CouncilResponse;
import io.festival.distance.domain.studentcouncil.dto.response.SchoolLocation;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.repository.CouncilRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilReader {

    private final CouncilRepository councilRepository;
    private final CouncilGpsReader councilGpsReader;
    private final CouncilImageReader councilImageReader;

    @Transactional(readOnly = true)
    public CouncilResponse findList(String school) {
        List<ContentResponse> contentResponseList = councilRepository.findAllBySchool(school)
            .stream()
            .map(studentCouncil -> {
                    List<CouncilGpsResponse> gpsList =
                        councilGpsReader.findGpsList(studentCouncil);
                    List<CouncilImageResponse> imageList = councilImageReader.findImageList(
                        studentCouncil);
                    return ContentResponse.toContentResponse(
                        studentCouncil,
                        gpsList,
                        imageList
                    );
                }
            ).toList();
        return CouncilResponse.toCouncilResponse(
            school,
            getSchoolLocation(school),
            contentResponseList
        );
    }

    @Transactional(readOnly = true)
    public ContentResponse findOne(Long studentCouncilId) {
        return councilRepository.findById(studentCouncilId)
            .map(studentCouncil -> {
                    List<CouncilGpsResponse> gpsList =
                        councilGpsReader.findGpsList(studentCouncil);
                    List<CouncilImageResponse> imageList = councilImageReader.findImageList(
                        studentCouncil);
                    return ContentResponse.toContentResponse(studentCouncil, gpsList, imageList);
                }
            ).orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_CONTENT));
    }

    @Transactional(readOnly = true)
    public StudentCouncil findStudentCouncil(Long studentCouncilId) {
        return councilRepository.findById(studentCouncilId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_CONTENT));
    }
}
