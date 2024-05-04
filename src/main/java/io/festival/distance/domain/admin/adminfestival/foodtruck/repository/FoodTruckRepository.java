package io.festival.distance.domain.admin.adminfestival.foodtruck.repository;

import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTruckRepository extends JpaRepository<FoodTruck,Long> {
    List<FoodTruck> findAllBySchool(String school);
}
