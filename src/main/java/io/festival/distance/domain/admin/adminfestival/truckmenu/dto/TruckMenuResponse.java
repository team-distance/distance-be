package io.festival.distance.domain.admin.adminfestival.truckmenu.dto;

import io.festival.distance.domain.admin.adminfestival.truckmenu.entity.TruckMenu;
import lombok.Builder;

@Builder
public record TruckMenuResponse(
    String menu,
    String menuImageUrl,
    int price
) {
    public static TruckMenuResponse fromEntity(TruckMenu truckMenu){
        return TruckMenuResponse.builder()
            .menu(truckMenu.getMenu())
            .menuImageUrl(truckMenu.getMenuImageUrl())
            .price(truckMenu.getPrice())
            .build();
    }
}
