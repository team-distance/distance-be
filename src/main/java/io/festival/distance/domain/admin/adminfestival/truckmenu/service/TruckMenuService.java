package io.festival.distance.domain.admin.adminfestival.truckmenu.service;

import static io.festival.distance.domain.admin.adminfestival.truckmenu.exception.TruckMenuErrorCode.NOT_EXIST_MENU;

import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import io.festival.distance.domain.admin.adminfestival.foodtruck.service.FoodTruckService;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuRequest;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuResponse;
import io.festival.distance.domain.admin.adminfestival.truckmenu.entity.TruckMenu;
import io.festival.distance.domain.admin.adminfestival.truckmenu.exception.TruckMenuException;
import io.festival.distance.domain.admin.adminfestival.truckmenu.repository.TruckMenuRepository;
import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.infra.s3.service.S3DeleteImage;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TruckMenuService {

    private final TruckMenuRepository truckMenuRepository;
    private final FoodTruckService foodTruckService;
    private final S3UploadImage s3UploadImage;
    private final S3DeleteImage s3DeleteImage;
    @Transactional
    public void saveTruckMenu(
        Long foodTruckId,
        TruckMenuRequest truckMenuRequest,
        MultipartFile file
    ) {
        FoodTruck foodTruck = foodTruckService.findFoodTruck(foodTruckId);
        S3Response s3Response = uploadTruckMenuImage(file);
        TruckMenu truckMenu = getTruckMenuEntity(truckMenuRequest, s3Response, foodTruck);
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
            .orElseThrow(() -> new TruckMenuException(NOT_EXIST_MENU));
    }

    @Transactional
    public void removeTruckMenu(Long truckMenuId) {
        String truckMenuFileName = findTruckMenuFileName(findTruckMenu(truckMenuId));
        deleteTruckMenuImage(truckMenuFileName);
        truckMenuRepository.deleteById(truckMenuId);
    }

    @Transactional
    public void modifyTruckMenu(
        TruckMenuRequest truckMenuRequest,
        MultipartFile file,
        Long truckMenuId
    ) {
        TruckMenu truckMenu = findTruckMenu(truckMenuId);
        S3Response s3Response = uploadTruckMenuImage(file);
        updateTruckMenuInfo(truckMenuRequest,s3Response,truckMenu);
    }

    public S3Response uploadTruckMenuImage(MultipartFile file) {
        return s3UploadImage.saveImage(file);
    }

    public void deleteTruckMenuImage(String truckFileName) {
        s3DeleteImage.deleteImage(truckFileName);
    }

    public String findTruckMenuFileName(TruckMenu truckMenu) {
        return truckMenu.getMenuFileName();
    }

    @Transactional
    public void updateTruckMenuInfo(
        TruckMenuRequest truckMenuRequest,
        S3Response s3Response,
        TruckMenu truckMenu
    ) {
        truckMenu.update(truckMenuRequest, s3Response);
    }

    private static TruckMenu getTruckMenuEntity(
        TruckMenuRequest truckMenuRequest,
        S3Response s3Response,
        FoodTruck foodTruck
    ) {
        return TruckMenu.builder()
            .menu(truckMenuRequest.menu())
            .menuImageUrl(s3Response.imageUrl())
            .foodTruck(foodTruck)
            .menuFileName(s3Response.fileName())
            .price(truckMenuRequest.price())
            .build();
    }
}
