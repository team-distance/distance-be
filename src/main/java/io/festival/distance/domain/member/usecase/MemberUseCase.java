package io.festival.distance.domain.member.usecase;

import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.member.validsignup.ValidInfoDto;
import io.festival.distance.domain.member.validsignup.ValidTelNum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUseCase {
    private final MemberService memberService;
    private final ValidInfoDto validInfoDto;
    private final ValidTelNum validLoginId;
    public Long execute(MemberSignDto signDto){
        validLoginId.duplicateCheckTelNum(signDto.telNum());
        validInfoDto.checkInfoDto(signDto);
        return memberService.createMember(signDto);
    }
}
