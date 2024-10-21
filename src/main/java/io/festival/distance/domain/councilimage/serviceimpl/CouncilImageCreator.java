package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.image.dto.response.S3UrlResponse;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouncilImageCreator {

    private final CouncilImageSaver councilImageSaver;
    public void create(
        List<S3UrlResponse> s3UrlResponses,
        List<Integer> priority,
        StudentCouncil studentCouncil
    ) {
        List<CouncilImage> councilImages = new ArrayList<>();
        System.out.println("s3UrlResponses = " + s3UrlResponses.get(1).s3Url());
        for (int i = 0; i < s3UrlResponses.size(); i++) {
            CouncilImage councilImage = CouncilImage.builder()
                .fileName(s3UrlResponses.get(i).fileName())
                .imageUrl("https://distance-buckets.s3.ap-northeast-2.amazonaws.com/" + s3UrlResponses.get(i).fileName())
                .imageHash("distance")
                .priority(priority.get(i))
                .isUsed(true)
                .studentCouncil(studentCouncil)
                .build();
            councilImages.add(councilImage);
        }
        councilImageSaver.saveAll(councilImages);
    }
}
