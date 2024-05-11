package io.festival.distance.domain.admin.adminfestival.foodtruck.controller;

import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.FoodTruckResponse;
import io.festival.distance.domain.admin.adminfestival.foodtruck.service.FoodTruckService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-truck")
@CrossOrigin
public class FoodTruckController {

    private final FoodTruckService foodTruckService;


    /** NOTE
     * Image 업로드 매소드
     */
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "foodTruckRequest") FoodTruckRequest foodTruckRequest
    ) {
        foodTruckService.saveTruck(foodTruckRequest, file);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 이미지 삭제
     */
    @DeleteMapping("/{foodTruckId}")
    public ResponseEntity<Void> deleteFoodTruck(@PathVariable Long foodTruckId) {
        foodTruckService.removeFoodTruck(foodTruckId);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 트럭 조회 API
     */
    @GetMapping
    public ResponseEntity<List<FoodTruckResponse>> getTrucks(@RequestParam String school){
        return ResponseEntity.ok(foodTruckService.getTruckList(school));
    }

    @PatchMapping(value = "/{foodTruckId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFoodTruck(
        @PathVariable Long foodTruckId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "foodTruckRequest") FoodTruckRequest foodTruckRequest
    ) {
        foodTruckService.modifyFoodTruck(foodTruckRequest, file, foodTruckId);
        return ResponseEntity.ok().build();
    }
}
