package io.festival.distance.domain.admin.adminfestival.truckmenu.service;

import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import io.festival.distance.domain.admin.adminfestival.foodtruck.service.FoodTruckService;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuRequest;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuResponse;
import io.festival.distance.domain.admin.adminfestival.truckmenu.entity.TruckMenu;
import io.festival.distance.domain.admin.adminfestival.truckmenu.repository.TruckMenuRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TruckMenuService {

    private final TruckMenuRepository truckMenuRepository;
    private final FoodTruckService foodTruckService;

    @Transactional
    public void saveTruckMenu(
        Long foodTruckId,
        TruckMenuRequest truckMenuRequest,
        S3Response response
    ) {
        FoodTruck foodTruck = foodTruckService.findFoodTruck(foodTruckId);
        TruckMenu truckMenu = TruckMenu.builder()
            .menu(truckMenuRequest.menu())
            .menuImageUrl(response.imageUrl())
            .foodTruck(foodTruck)
            .menuFileName(response.fileName())
            .price(truckMenuRequest.price())
            .build();
        truckMenuRepository.save(truckMenu);
    }


    @Transactional(readOnly = true)
    public List<TruckMenuResponse> getListMenu(Long foodTruckId) {
        return truckMenuRepository.findAllByFoodTruckFoodTruckId(foodTruckId)
            .stream()
            .map(TruckMenuResponse::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public TruckMenu findTruckMenu(Long truckMenuId) {
        return truckMenuRepository.findById(truckMenuId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    @Transactional
    public void removeTruckMenu(Long truckMenuId) {
        truckMenuRepository.deleteById(truckMenuId);
    }

    @Transactional
    public void modifyTruckMenu(
        TruckMenuRequest truckMenuRequest,
        S3Response response,
        Long truckMenuId
    ) {
        TruckMenu truckMenu = findTruckMenu(truckMenuId);
        truckMenu.update(truckMenuRequest,response);
    }
}
