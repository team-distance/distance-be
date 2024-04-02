package io.festival.distance.domain.admin.dto;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import java.util.List;

public record AdminSignUpDto(
    String telNum,
    String password,
    String checkPassword
) {

}
