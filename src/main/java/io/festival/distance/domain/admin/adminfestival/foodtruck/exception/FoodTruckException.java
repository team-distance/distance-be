package io.festival.distance.domain.admin.adminfestival.foodtruck.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodTruckException extends RuntimeException{
    FoodTruckErrorCode errorCode;
}
