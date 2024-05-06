package io.festival.distance.domain.admin.adminfestival.truckmenu.controller;

import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuRequest;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuResponse;
import io.festival.distance.domain.admin.adminfestival.truckmenu.service.TruckMenuService;
import io.festival.distance.infra.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/truck-menu")
@CrossOrigin
@RequiredArgsConstructor
public class TruckMenuController {
    private final TruckMenuService truckMenuService;
    private final S3UploadImage s3UploadImage;


    /** NOTE
     * 푸드트럭 메뉴 이미지 등록 API
     * @param foodTruckId
     * @param file
     * @param truckMenuRequest
     * @return
     */
    @PostMapping(value = "/{foodTruckId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
        @PathVariable Long foodTruckId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "truckMenuRequest") TruckMenuRequest truckMenuRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file);
        truckMenuService.saveTruckMenu(foodTruckId,truckMenuRequest, response);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 푸드트럭 메뉴 조회
     * @return
     */
    @GetMapping("/{foodTruckId}")
    public ResponseEntity<List<TruckMenuResponse>> getMenus(@PathVariable Long foodTruckId) {
        return ResponseEntity.ok(truckMenuService.getListMenu(foodTruckId));
    }

    /** NOTE
     * 메뉴 이미지 삭제
     * @param truckMenuId
     * @return
     */
    @DeleteMapping("/{truckMenuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long truckMenuId){
        String menuFileName = truckMenuService.findTruckMenu(truckMenuId).getMenuFileName();
        s3UploadImage.deleteImage(menuFileName);
        truckMenuService.removeTruckMenu(truckMenuId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{truckMenuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFoodTruck(
        @PathVariable Long truckMenuId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "truckMenuRequest") TruckMenuRequest truckMenuRequest
    ) {
        S3Response response = s3UploadImage.saveImage(file);
        truckMenuService.modifyTruckMenu(truckMenuRequest, response, truckMenuId);
        return ResponseEntity.ok().build();
    }
}
