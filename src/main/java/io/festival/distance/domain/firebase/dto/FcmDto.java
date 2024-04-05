package io.festival.distance.domain.firebase.dto;

import lombok.Builder;

@Builder
public record FcmDto(String clientToken,
                     String senderNickName,
                     String message) {

}
