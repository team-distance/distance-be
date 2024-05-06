package io.festival.distance.domain.admin.adminfestival.foodtruck.controller;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.foodtruck.service.FoodTruckService;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-truck")
@CrossOrigin
public class FoodTruckController {

    private final S3UploadImage s3UploadImage;
    private final FoodTruckService foodTruckService;


    /**
     * NOTE
     * Image 업로드 매소드
     * @param file 이미지 파일
     * @return
     */
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "foodTruckRequest") FoodTruckRequest foodTruckRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file);
        foodTruckService.saveTruck(foodTruckRequest, response);
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 이미지 삭제
     * @return
     */
    @DeleteMapping("/{foodTruckId}")
    public ResponseEntity<Void> deleteFoodTruck(@PathVariable Long foodTruckId) {
        String truckFileName = foodTruckService.findFoodTruck(foodTruckId).getTruckFileName();
        s3UploadImage.deleteImage(truckFileName);
        foodTruckService.removeFoodTruck(foodTruckId);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 트럭 조회 API
     * @param school
     * @return
     */
    @GetMapping
    public ResponseEntity<List<FoodTruckResponse>> getTrucks(@RequestParam String school){
        return ResponseEntity.ok(foodTruckService.getTruckList(school));
    }

    @PatchMapping(value = "/{foodTruckId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFoodTruck(
        @PathVariable Long foodTruckId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "foodTruckRequest") FoodTruckRequest foodTruckRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file);
        foodTruckService.modifyFoodTruck(foodTruckRequest, response, foodTruckId);
        return ResponseEntity.ok().build();
    }

}
