package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.infra.s3.service.S3DeleteImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CouncilImageUpdater {

    private final CouncilImageReader councilImageReader;
    private final S3DeleteImage s3DeleteImage;
    private final CouncilImageDeleter councilImageDeleter;
    private final CouncilImageCreator councilImageCreator;

    @Transactional
    public void deleteImage(StudentCouncil studentCouncil) {
        councilImageReader.findImageEntity(studentCouncil)
            .forEach(councilImage -> {
                s3DeleteImage.deleteImage(councilImage.getFileName());
            });
        councilImageDeleter.delete(studentCouncil);
    }

    @Transactional
    public void update(List<MultipartFile> files, StudentCouncil studentCouncil) {
        deleteImage(studentCouncil);
        councilImageCreator.create(files,studentCouncil);
    }
}
