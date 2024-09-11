package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.councilimage.repository.CouncilImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouncilImageSaver {
    private final CouncilImageRepository councilImageRepository;

    @Transactional
    public void saveAll(List<CouncilImage> councilImages){
        councilImageRepository.saveAll(councilImages);
    }
}
