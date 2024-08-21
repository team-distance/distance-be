package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcouncil.dto.request.ContentRequest;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouncilCreator {
    private final CouncilSaver councilSaver;

    public StudentCouncil create(ContentRequest contentRequest, Member member){
        StudentCouncil studentCouncil = StudentCouncil.builder()
            .title(contentRequest.title())
            .content(contentRequest.content())
            .startDt(contentRequest.startDt())
            .endDt(contentRequest.endDt())
            .school(member.getSchool())
            .member(member)
            .build();
        councilSaver.save(studentCouncil);
        return studentCouncil;
    }
}
