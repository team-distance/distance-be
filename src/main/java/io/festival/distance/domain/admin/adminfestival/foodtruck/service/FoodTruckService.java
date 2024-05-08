package io.festival.distance.domain.admin.adminfestival.foodtruck.service;

import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import io.festival.distance.domain.admin.adminfestival.foodtruck.repository.FoodTruckRepository;
import io.festival.distance.domain.admin.adminfestival.truckmenu.service.TruckMenuService;
import io.festival.distance.exception.DistanceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    @Transactional
    public void saveTruck(
        FoodTruckRequest foodTruckRequest,
        S3Response s3Response
    ) {
        FoodTruck truck = FoodTruck.builder()
            .truckName(foodTruckRequest.truckName())
            .foodTruckImageUrl(s3Response.imageUrl())
            .description(foodTruckRequest.description())
            .truckFileName(s3Response.fileName())
            .school(foodTruckRequest.school())
            .build();
        foodTruckRepository.save(truck);
    }

    @Transactional(readOnly = true)
    public FoodTruck findFoodTruck(Long truckId){
        return foodTruckRepository.findById(truckId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
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
        foodTruckRepository.deleteById(foodTruckId);
    }

    @Transactional
    public void modifyFoodTruck(
        FoodTruckRequest foodTruckRequest,
        S3Response response,
        Long foodTruckId
    ) {
        FoodTruck foodTruck = findFoodTruck(foodTruckId);
        foodTruck.update(foodTruckRequest,response);
    }
}
