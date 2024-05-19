package io.festival.distance.infra.s3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class S3Exception extends RuntimeException{
    S3ErrorCode errorCode;
}
