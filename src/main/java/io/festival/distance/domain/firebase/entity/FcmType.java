package io.festival.distance.domain.firebase.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FcmType {
    MESSAGE("message"),
    STUDENT_CARD("student_card"),
    WAITING("waiting");

    public final String type;
}
