package io.festival.distance.domain.statistics.entity;

import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import java.time.LocalDate;
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
@Table(name = "council_statistics")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CouncilStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    private Long statisticsId;

    @Column(name = "count")
    private Integer count;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "role")
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "council_id")
    private StudentCouncil studentCouncil;

    public void updateCount(int count){
        this.count =count;
    }
}

