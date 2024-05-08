package io.festival.distance.domain.admin.adminfestival.truckmenu.service;

import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuResponse;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuCachingService {
    private final TruckMenuService truckMenuService;

    @Cacheable(cacheNames = "menus")
    public List<TruckMenuResponse> getListMenu(Long foodTruckId) {
        return truckMenuService.getListMenu(foodTruckId);
    }
}
