package io.festival.distance.domain.councilimage.serviceimpl;

import io.festival.distance.domain.councilimage.entity.CouncilImage;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CouncilImageUpdater {

    private final CouncilImageReader councilImageReader;
    private final CouncilImageCreator councilImageCreator;
    private final S3UploadImage s3UploadImage;

    @Transactional
    public void update(List<MultipartFile> files, StudentCouncil studentCouncil)
        throws IOException, NoSuchAlgorithmException {
        List<CouncilImage> imageList = councilImageReader.findImageEntity(studentCouncil);

        List<CouncilImage> imagesToDelete = new ArrayList<>();
        List<MultipartFile> filesToUpload = new ArrayList<>();

        Map<String, MultipartFile> fileHashMap = new HashMap<>();
        for (MultipartFile file : files) {
            String fileHash = s3UploadImage.calculateMD5(file);
            fileHashMap.put(fileHash, file);
        }

        for (CouncilImage councilImage : imageList) {
            if (fileHashMap.containsKey(councilImage.getImageHash())) {
                fileHashMap.remove(councilImage.getImageHash());
            } else {
                imagesToDelete.add(councilImage);
            }
        }

        for (CouncilImage imageToDelete : imagesToDelete) {
            imageToDelete.updateIsUsed();
        }
        filesToUpload.addAll(fileHashMap.values());
        councilImageCreator.create(filesToUpload, studentCouncil);
    }
}