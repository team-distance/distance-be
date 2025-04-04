package io.festival.distance.infra.sms;

import static io.festival.distance.infra.sms.SmsUtil.getMessage;

import javax.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FindMessageService implements MessageService {

    @Value("${coolsms.find-api.key}")
    private String findApiKey;
    @Value("${coolsms.find-api.secret}")
    private String findApiSecretKey;
    @Value("${coolsms.find-api.number}")
    private String findNumber;

    @PostConstruct
    private void findInit() {
        this.findMessageService = NurigoApp.INSTANCE.initialize(
            findApiKey,
            findApiSecretKey,
            "https://api.coolsms.co.kr"
        );
    }

    private DefaultMessageService findMessageService;

    @Override
    public void sendMessage(String telNum, String verificationCode) {
        findMessageService.sendOne(
            new SingleMessageSendingRequest(
                getMessage(
                    telNum,
                    verificationCode,
                    findNumber
                )
            )
        );
    }
    @Override
    public MessageType getType() {
        return MessageType.FIND;
    }
}
