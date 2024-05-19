package io.festival.distance.domain.studentcard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentCardException extends RuntimeException{
    StudentCardErrorCode errorCode;
}
