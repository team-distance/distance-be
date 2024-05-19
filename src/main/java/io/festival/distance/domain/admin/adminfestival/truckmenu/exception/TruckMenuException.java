package io.festival.distance.domain.admin.adminfestival.truckmenu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TruckMenuException extends RuntimeException{
    TruckMenuErrorCode errorCode;
}
