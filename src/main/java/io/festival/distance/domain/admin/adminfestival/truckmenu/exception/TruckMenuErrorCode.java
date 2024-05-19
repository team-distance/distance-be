package io.festival.distance.domain.admin.adminfestival.truckmenu.exception;

import io.festival.distance.global.exception.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TruckMenuErrorCode {
    NOT_EXIST_MENU(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 메뉴입니다!");
    private final int status;
    private final String message;
}
