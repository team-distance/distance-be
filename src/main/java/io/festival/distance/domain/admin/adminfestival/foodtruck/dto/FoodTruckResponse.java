package io.festival.distance.domain.admin.adminfestival.foodtruck.dto;

import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import lombok.Builder;

@Builder
public record FoodTruckResponse(
    Long foodTruckId,
    String truckName,
    String foodTruckImageUrl,
    String description
) {
    public static FoodTruckResponse fromEntity(FoodTruck foodTruck){
        return FoodTruckResponse.builder()
            .foodTruckId(foodTruck.getFoodTruckId())
            .truckName(foodTruck.getTruckName())
            .foodTruckImageUrl(foodTruck.getFoodTruckImageUrl())
            .description(foodTruck.getDescription())
            .build();
    }
}
