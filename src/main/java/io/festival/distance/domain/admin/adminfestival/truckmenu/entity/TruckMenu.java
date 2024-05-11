package io.festival.distance.domain.admin.adminfestival.truckmenu.entity;

import io.festival.distance.infra.s3.dto.S3Response;
import io.festival.distance.domain.admin.adminfestival.foodtruck.entity.FoodTruck;
import io.festival.distance.domain.admin.adminfestival.truckmenu.dto.TruckMenuRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "truck_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TruckMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu")
    private String menu;

    @Column(name = "menu_image_url")
    private String menuImageUrl;

    @Column(name = "menu_file_name")
    private String menuFileName;

    @Column(name = "price")
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    public void update(TruckMenuRequest truckMenuRequest, S3Response response){
        this.menu=truckMenuRequest.menu();
        this.menuFileName=response.fileName();
        this.price=truckMenuRequest.price();
        this.menuImageUrl=response.imageUrl();
    }
}
