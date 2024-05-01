package io.festival.distance.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnivCert {

    SUCCESS("SUCCESS","인증되었습니다!"),
    FAILED_1("FAILED_1","학생증 인증이 안되었습니다!"),
    FAILED_2("FAILED_2","사진이 흔들렸습니다!"),
    FAILED_3("FAILED_3","해당 학교 학생증이 아닙니다!"),
    FAILED_4("FAILED_4","사용자가 등록한 성별과 다릅니다!");
    private final String type;
    private final String message;

}
