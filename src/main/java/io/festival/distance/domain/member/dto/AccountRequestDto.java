package io.festival.distance.domain.member.dto;

public record AccountRequestDto(String telNum,
                                String password,
                                String checkPassword,
                                String gender
) {
}
