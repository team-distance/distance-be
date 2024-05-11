package io.festival.distance.domain.admin.adminfestival.truckmenu.controller;

import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuRequest;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuResponse;
import io.festival.distance.domain.admin.adminfestival.truckmenu.service.TruckMenuService;
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

    /** NOTE
     * 푸드트럭 메뉴 이미지 등록 API
     */
    @PostMapping(value = "/{foodTruckId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
        @PathVariable Long foodTruckId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "truckMenuRequest") TruckMenuRequest truckMenuRequest
    ) {
        truckMenuService.saveTruckMenu(foodTruckId,truckMenuRequest, file);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 푸드트럭 메뉴 조회
     */
    @GetMapping("/{foodTruckId}")
    public ResponseEntity<List<TruckMenuResponse>> getMenus(@PathVariable Long foodTruckId) {
        return ResponseEntity.ok(truckMenuService.getListMenu(foodTruckId));
    }

    /** NOTE
     * 메뉴 이미지 삭제
     */
    @DeleteMapping("/{truckMenuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long truckMenuId){
        truckMenuService.removeTruckMenu(truckMenuId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{truckMenuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFoodTruck(
        @PathVariable Long truckMenuId,
        @RequestPart(value = "file", required = false) MultipartFile file,
        @RequestPart(value = "truckMenuRequest") TruckMenuRequest truckMenuRequest
    ) {
        truckMenuService.modifyTruckMenu(truckMenuRequest, file, truckMenuId);
        return ResponseEntity.ok().build();
    }
}
