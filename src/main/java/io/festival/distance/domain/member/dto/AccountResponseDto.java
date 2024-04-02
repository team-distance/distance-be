package io.festival.distance.domain.member.dto;

import lombok.Builder;

@Builder
public record AccountResponseDto(String password,
                                 String gender,
                                 String telNum) {

}
