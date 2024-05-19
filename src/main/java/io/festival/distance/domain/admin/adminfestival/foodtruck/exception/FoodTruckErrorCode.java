package io.festival.distance.domain.admin.adminfestival.foodtruck.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodTruckErrorCode {
    NOT_EXIST_FOOD_TRUCK(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 푸드트럭입니다!");
    private final int status;
    private final String message;
}
