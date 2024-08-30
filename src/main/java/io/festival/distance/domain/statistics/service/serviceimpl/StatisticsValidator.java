package io.festival.distance.domain.statistics.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class StatisticsValidator {
    public void validateAuthority(StudentCouncil studentCouncil, Member member){
        if(!studentCouncil.getAuthority().equals(member.getAuthority()))
            throw new DistanceException(ErrorCode.NOT_AUTHENTICATE_COUNCIL);
    }
}
