package io.festival.distance.domain.admin.adminfestival.foodtruck.service;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_FOOD_TRUCK;

import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import io.festival.distance.domain.admin.adminfestival.foodtruck.repository.FoodTruckRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3DeleteImage;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    private final S3UploadImage s3UploadImage;
    private final S3DeleteImage s3DeleteImage;

    @Transactional
    public void saveTruck(
        FoodTruckRequest foodTruckRequest,
        MultipartFile file
    ) {
        S3Response s3Response = uploadFoodTruckImage(file);
        FoodTruck truck = getFoodTruckEntity(
            foodTruckRequest, s3Response);
        foodTruckRepository.save(truck);
    }

    @Transactional(readOnly = true)
    public FoodTruck findFoodTruck(Long truckId) {
        return foodTruckRepository.findById(truckId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_FOOD_TRUCK));
    }

    @Transactional(readOnly = true)
    public List<FoodTruckResponse> getTruckList(String school) {
        return foodTruckRepository.findAllBySchool(school)
            .stream()
            .map(FoodTruckResponse::fromEntity)
            .toList();
    }

    @Transactional
    public void removeFoodTruck(Long foodTruckId) {
        String truckFileName = findTruckFileName(findFoodTruck(foodTruckId));
        deleteFoodTruckImage(truckFileName);
        foodTruckRepository.deleteById(foodTruckId);
    }

    @Transactional
    public void modifyFoodTruck(
        FoodTruckRequest foodTruckRequest,
        MultipartFile file,
        Long foodTruckId
    ) {
        FoodTruck foodTruck = findFoodTruck(foodTruckId);
        S3Response s3Response = uploadFoodTruckImage(file);
        updateFoodTruckInfo(foodTruckRequest, s3Response, foodTruck);
    }

    public S3Response uploadFoodTruckImage(MultipartFile file) {
        return s3UploadImage.saveImage(file);
    }

    public void deleteFoodTruckImage(String truckFileName) {
        s3DeleteImage.deleteImage(truckFileName);
    }

    public String findTruckFileName(FoodTruck foodTruck) {
        return foodTruck.getTruckFileName();
    }

    @Transactional
    public void updateFoodTruckInfo(
        FoodTruckRequest foodTruckRequest,
        S3Response s3Response,
        FoodTruck foodTruck
    ) {
        foodTruck.update(foodTruckRequest, s3Response);
    }

    private static FoodTruck getFoodTruckEntity(FoodTruckRequest foodTruckRequest,
        S3Response s3Response) {
        return FoodTruck.builder()
            .truckName(foodTruckRequest.truckName())
            .foodTruckImageUrl(s3Response.imageUrl())
            .description(foodTruckRequest.description())
            .truckFileName(s3Response.fileName())
            .school(foodTruckRequest.school())
            .build();
    }
}
