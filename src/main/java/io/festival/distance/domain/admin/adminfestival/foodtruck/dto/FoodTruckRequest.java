package io.festival.distance.domain.admin.adminfestival.foodtruck.dto;

import org.springframework.web.multipart.MultipartFile;

public record FoodTruckRequest(
    MultipartFile file,
    String truckName,
    String description,
    String school
    ) {

}
