package io.festival.distance.domain.member.dto;

public record MemberProfileDto(String schoolEmail,
                               String password,
                               String gender,
                               String nickName,
                               String telNum,
                               String school,
                               String college,
                               String department) {
}
