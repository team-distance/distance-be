package io.festival.distance.domain.member.entity;

import io.festival.distance.authuniversity.domain.University;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnivCert {

    SUCCESS("SUCCESS","인증되었습니다!"),
    FAILED_1("FAILED_1","학생증 인증이 안되었습니다!"),
    FAILED_2("FAILED_2","사진이 흔들렸습니다!"),
    FAILED_3("FAILED_3","해당 학교 학생증이 아닙니다!");
    private final String type;
    private final String message;

}
