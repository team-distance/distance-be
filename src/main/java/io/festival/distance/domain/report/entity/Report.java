package io.festival.distance.domain.report.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "report_content")
    private String reportContent;

    @JoinColumn(name = "my_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member me;

    @JoinColumn(name = "opponent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member opponent;
}
