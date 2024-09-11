package io.festival.distance.domain.councilgps.entity;

import io.festival.distance.domain.councilgps.dto.request.CouncilGpsRequest;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
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
@Table(name = "council_gps")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouncilGps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "council_gps_id")
    private Long councilGpsId;

    @Column(name = "council_latitude")
    private Double councilLatitude;

    @Column(name = "council_longitude")
    private Double councilLongitude;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "council_id")
    private StudentCouncil studentCouncil;

    public void updateGps(CouncilGpsRequest councilGpsRequest){
        this.councilLatitude = councilGpsRequest.councilLatitude();
        this.councilLongitude = councilGpsRequest.councilLongitude();
        this.location = councilGpsRequest.location();
    }
}
