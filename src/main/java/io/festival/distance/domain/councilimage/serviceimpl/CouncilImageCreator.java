package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CouncilImageCreator {
    private final CouncilImageSaver councilImageSaver;
    private final S3UploadImage s3UploadImage;

    public void create(
        List<MultipartFile> files,
        StudentCouncil studentCouncil,
        List<Integer> priority
    ){
        List<CouncilImage> councilImages = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            S3Response s3Response = s3UploadImage.saveImage(files.get(i));
            CouncilImage councilImage = CouncilImage.builder()
                .fileName(s3Response.fileName())
                .imageUrl(s3Response.imageUrl())
                .imageHash(s3Response.imageHash())
                .priority(priority.get(i))
                .isUsed(true)
                .studentCouncil(studentCouncil)
                .build();
            councilImages.add(councilImage);
        }
        councilImageSaver.saveAll(councilImages);
    }
}
