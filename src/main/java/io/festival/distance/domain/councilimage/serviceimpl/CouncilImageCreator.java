package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CouncilImageCreator {
    private final CouncilImageSaver councilImageSaver;
    private final S3UploadImage s3UploadImage;
    public void create(List<MultipartFile> files, StudentCouncil studentCouncil){
        List<CouncilImage> councilImages = new ArrayList<>();
        for (MultipartFile file : files) {
            S3Response s3Response = s3UploadImage.saveImage(file);
            CouncilImage councilImage = CouncilImage.builder()
                .fileName(s3Response.fileName())
                .imageUrl(s3Response.imageUrl())
                .studentCouncil(studentCouncil)
                .build();
            councilImages.add(councilImage);
        }
        councilImageSaver.saveAll(councilImages);
    }
}
