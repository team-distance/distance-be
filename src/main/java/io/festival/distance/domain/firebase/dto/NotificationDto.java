package io.festival.distance.domain.firebase.dto;

import lombok.Builder;

@Builder
public record NotificationDto(String FcmMessageId){
}
