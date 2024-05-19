package io.festival.distance.domain.report.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportException extends RuntimeException{
    ReportErrorCode errorCode;
}
