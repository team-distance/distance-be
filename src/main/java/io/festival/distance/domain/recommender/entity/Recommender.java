package io.festival.distance.domain.recommender.entity;

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
@Table(name = "recommender")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Recommender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommender_id")
    private Long recommendId;

    @Column(name = "referrer_id")
    private Long referrerId;

    @Column(name = "referred_tel")
    private String referredTel;
}
