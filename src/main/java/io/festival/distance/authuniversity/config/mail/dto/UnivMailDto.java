package io.festival.distance.authuniversity.config.mail.dto;

import lombok.Builder;


@Builder
public record UnivMailDto(
    String mailAddress,
    String title,
    String message,
    String tempPw
) {

}
