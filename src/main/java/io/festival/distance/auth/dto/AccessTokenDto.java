package io.festival.distance.auth.dto;

import lombok.Builder;

@Builder
public record AccessTokenDto(String accessToken) {

}
