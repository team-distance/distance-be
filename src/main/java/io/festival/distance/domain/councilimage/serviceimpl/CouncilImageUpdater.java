package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.infra.s3.service.S3DeleteImage;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CouncilImageUpdater {
    private final CouncilImageReader councilImageReader;
    private final S3UploadImage s3UploadImage;
    private final S3DeleteImage s3DeleteImage;

    public void deleteImage(List<String> fileName){

    }
}
