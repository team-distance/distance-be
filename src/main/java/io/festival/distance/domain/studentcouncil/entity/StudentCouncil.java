package io.festival.distance.domain.studentcouncil.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "student_council")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentCouncil extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "council_id")
    private Long councilId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_dt")
    private LocalDate startDt;

    @Column(name = "end_dt")
    private LocalDate endDt;

    @Column(name = "school")
    private String school;

    @Column(name = "authority")
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateContent(ContentRequest contentRequest) {
        this.title = contentRequest.title();
        this.content = contentRequest.content();
        this.startDt = contentRequest.startDt();
        this.endDt = contentRequest.endDt();
    }
}
