package io.festival.distance.domain.member.dto;

import lombok.Builder;

@Builder
public record ChangePasswordDto(String password,
                                String telNum) {
}
