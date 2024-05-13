package io.festival.distance.domain.member.usecase;

import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.member.validsignup.ValidInfoDto;
import io.festival.distance.domain.member.validsignup.ValidTelNum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUseCase {
    private final MemberService memberService;
    private final ValidInfoDto validInfoDto;
    private final ValidTelNum validLoginId;
    @Transactional
    public Long execute(MemberSignDto signDto){
        ValidMemberTelNum(signDto.telNum());
        ValidMemberInfo(signDto);
        return memberService.createMember(signDto);
    }

    public void ValidMemberTelNum(String telNum){
        validLoginId.duplicateCheckTelNum(telNum);
    }

    public void ValidMemberInfo(MemberSignDto signDto){
        validInfoDto.checkInfoDto(signDto);
    }
}
