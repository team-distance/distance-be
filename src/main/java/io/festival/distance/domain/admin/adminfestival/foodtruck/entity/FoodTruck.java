package io.festival.distance.domain.admin.adminfestival.foodtruck.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_truck")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FoodTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_truck_id")
    private Long foodTruckId;

    @Column(name = "truck_name")
    private String truckName;

    @Column(name = "food_truck_image_url")
    private String foodTruckImageUrl;

    @Column(name = "truck_file_name")
    private String truckFileName;

    @Column(name = "description")
    private String description;

    @Column(name = "school")
    private String school;
}
