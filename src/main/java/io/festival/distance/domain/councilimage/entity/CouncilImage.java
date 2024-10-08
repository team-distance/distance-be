package io.festival.distance.domain.councilimage.entity;

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
@Table(name = "council_image")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouncilImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "council_image_id")
    private Long councilImageId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "image_hash")
    private String imageHash;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "council_id")
    private StudentCouncil studentCouncil;

    public void updateIsUsed(){
        this.isUsed=false;
    }
}
