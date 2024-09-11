package io.festival.distance.domain.studentcouncil.service.serviceimpl;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class CouncilValidator {
    public void isWriter(Member writer, Member member){
        if(!writer.getSchool().equals(member.getSchool())){
            throw new DistanceException(ErrorCode.NOT_AUTHENTICATE_COUNCIL);
        }
    }
}
