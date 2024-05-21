package io.festival.distance.domain.member.dto;

import java.util.List;

public record MemberSignDto(
    String telNum,
    String password,
    String gender,
    String mbti,
    String school,
    String department,
    String college,
    String memberCharacter,
    List<MemberTagDto> memberTagDto,
    List<MemberHobbyDto> memberHobbyDto
) {

}
