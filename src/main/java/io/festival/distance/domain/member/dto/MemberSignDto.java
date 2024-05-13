package io.festival.distance.domain.member.dto;

import java.util.List;

/**
 * NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
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
