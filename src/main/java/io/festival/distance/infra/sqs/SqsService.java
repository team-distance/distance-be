package io.festival.distance.infra.sqs;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SqsService {

    private final SqsClient sqsClient;
    @Value("${cloud.aws.sqs.queue.url}")
    private String sqsUrl;

    public final static String SYSTEM_ICON = "DISTANCE";
    @Autowired
    public SqsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(
        String clientToken,
        String title,
        String message,
        String imageUrl,
        String icon
    ) {
        String messageBody = String.format(
            "{\"clientToken\": \"%s\", \"message\": \"%s\",\"title\": \"%s\",\"imageUrl\": \"%s\",\"icon\": \"%s\"}",
            clientToken, message, title, imageUrl, icon
        );

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(sqsUrl)
            .messageBody(messageBody)
            .messageGroupId("distance-message-group")
            .messageDeduplicationId(UUID.randomUUID().toString())
            .build();

        sqsClient.sendMessage(sendMsgRequest);
    }
}
