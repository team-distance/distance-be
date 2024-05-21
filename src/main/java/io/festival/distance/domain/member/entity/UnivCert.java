package io.festival.distance.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnivCert {

    SUCCESS("SUCCESS","인증되었습니다!"),
    PENDING("PENDING","인증 중 입니다!"),
    FAILED_1("FAILED_1","학생증 인증이 안되었습니다!"),
    FAILED_2("FAILED_2","사진이 흔들렸습니다!"),
    FAILED_3("FAILED_3","해당 학교 학생증이 아닙니다!"),
    FAILED_4("FAILED_4","성별이 다릅니다! 회원 탈퇴 후 다시 가입해주세요."),
    FAILED_5("FAILED_5","학과가 다릅니다! 회원 탈퇴 후 다시 가입해주세요."),
    FAILED_6("FAILED_6","사진이 가려졌습니다!"),
    FAILED_7("FAILED_7","학교가 다릅니다! 회원 탈퇴 후 다시 가입해주세요.");
    private final String type;
    private final String message;

}
