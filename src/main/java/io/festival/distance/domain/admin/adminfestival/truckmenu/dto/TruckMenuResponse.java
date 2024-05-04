package io.festival.distance.domain.admin.adminfestival.truckmenu.dto;

import io.festival.distance.domain.admin.adminfestival.truckmenu.entity.TruckMenu;
import lombok.Builder;

@Builder
public record TruckMenuResponse(
    Long truckMenuId,
    String menu,
    String menuImageUrl,
    int price
) {
    public static TruckMenuResponse fromEntity(TruckMenu truckMenu){
        return TruckMenuResponse.builder()
            .truckMenuId(truckMenu.getMenuId())
            .menu(truckMenu.getMenu())
            .menuImageUrl(truckMenu.getMenuImageUrl())
            .price(truckMenu.getPrice())
            .build();
    }
}
