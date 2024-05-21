package io.festival.distance.utils.slack;

import static com.slack.api.webhook.WebhookPayloads.payload;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackService {
    private final Slack slackClient = Slack.getInstance();
    @Value("${slack.webhook.url}")
    private String webhookUrl;

    @Async
    public void sendSlackAlertErrorLog(Exception e, HttpServletRequest request) {
        try {
            slackClient.send(webhookUrl, payload(p -> p
                .text("Server Error!!")
                .attachments(
                    List.of(generateSlackAttachment(e, request))
                )
            ));
        } catch (IOException slackError) {
            log.error("Slack 통신과의 예외 발생");
        }
    }

    private Attachment generateSlackAttachment(Exception e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(
            LocalDateTime.now());
        String xffHeader = request.getHeader("X-FORWARDED-FOR");
        return Attachment.builder()
            .color("ff0000")
            .title(requestTime + " 발생 에러 로그")
            .fields(List.of(
                    generateSlackField("Request IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                    generateSlackField("Request URL", request.getRequestURL() + " " + request.getMethod()),
                    generateSlackField("Error Message", e.getMessage())
                )
            )
            .build();
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build();
    }
}
