package io.festival.distance.global.exception;

import io.festival.distance.utils.slack.SlackService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler(DistanceException.class)
    protected ResponseEntity<ErrorResponseEntity> errorCodeResponseEntity(DistanceException e,
        HttpServletRequest request) {
        log.error("UnhandledException: {} {}    errMessage = {}\n",
            request.getMethod(),
            request.getRequestURI(),
            e.getErrorCode().getMessage()
        );
        return ErrorResponseEntity.responseEntity(e.getErrorCode());
    }



    @ExceptionHandler(ChatRoomException.class)
    protected ResponseEntity<ErrorResponseEntity> errorCodeResponseEntity1(ChatRoomException e,
        HttpServletRequest request) {
        log.error("UnhandledException: {} {}    errMessage = {}\n",
            request.getMethod(),
            request.getRequestURI(),
            e.getErrorCode().getMessage()
        );
        return ErrorResponseEntity.responseEntity(e.getErrorCode());
    }

    //Slack 전송
    @ExceptionHandler(Exception.class)
    public void SlackErrorMessage(Exception e,HttpServletRequest request){
        log.error("UnhandledException: {} {} errMessage={}\n",
            request.getMethod(),
            request.getRequestURI(),
            e.getMessage()
        );
        slackService.sendSlackAlertErrorLog(e,request);
    }
}
