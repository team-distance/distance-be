package io.festival.distance.domain.admin.adminfestival.truckmenu.repository;

import io.festival.distance.domain.admin.adminfestival.truckmenu.entity.TruckMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckMenuRepository extends JpaRepository<TruckMenu,Long> {
    List<TruckMenu> findAllByFoodTruckFoodTruckId(Long foodTruckId);
}
