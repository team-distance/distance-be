package io.festival.distance.domain.admin.adminfestival.foodtruck.service;

import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TruckCachingService {

    private final FoodTruckService foodTruckService;

    @Cacheable(cacheNames = "foodtrucks")
    public List<FoodTruckResponse> getCacheTruckList(String school) {
        return foodTruckService.getTruckList(school);
    }
}
