package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import io.festival.distance.domain.councilgps.serviceimpl.CouncilGpsUpdater;
import io.festival.distance.domain.councilimage.serviceimpl.CouncilImageUpdater;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouncilUpdater {
    private final CouncilImageUpdater councilImageUpdater;
    private final CouncilGpsUpdater councilGpsUpdater;

    @Transactional
    public void update(
        ContentRequest contentRequest,
        List<MultipartFile> files,
        List<Integer> priority,
        StudentCouncil studentCouncil
    ) throws IOException, NoSuchAlgorithmException {
        double startTime = System.currentTimeMillis();
        studentCouncil.updateContent(contentRequest);
        councilGpsUpdater.update(contentRequest.councilGpsRequestList(),studentCouncil);
        councilImageUpdater.update(files,priority,studentCouncil);
        double endTime = System.currentTimeMillis();
        log.info("Execution Time : "+ (endTime - startTime)+"ms");
    }
}
