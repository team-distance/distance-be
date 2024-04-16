package io.festival.distance.domain.member.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record MemberProfileDto(String mbti,
                               String memberCharacter,
                               String department,
                               List<MemberTagDto> memberTagDto,
                               List<MemberHobbyDto> memberHobbyDto) {
}
