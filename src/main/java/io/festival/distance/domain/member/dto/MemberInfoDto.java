package io.festival.distance.domain.member.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberInfoDto(String mbti,
                            String memberCharacter,
                            List<MemberTagDto> memberTagDto,
                            List<MemberHobbyDto> memberHobbyDto) {
}
