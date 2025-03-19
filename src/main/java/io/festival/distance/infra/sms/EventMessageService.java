package io.festival.distance.infra.sms;

import static io.festival.distance.infra.sms.SmsUtil.sendEventMessage;

import javax.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventMessageService {
    @Value("${coolsms.find-api.key}")
    private String eventApiKey;
    @Value("${coolsms.find-api.secret}")
    private String eventApiSecretKey;
    @Value("${coolsms.find-api.number}")
    private String eventNumber;

    @PostConstruct
    private void eventInit() {
        this.eventMessageService = NurigoApp.INSTANCE.initialize(
            eventApiKey,
            eventApiSecretKey,
            "https://api.coolsms.co.kr"
        );
    }

    private DefaultMessageService eventMessageService;
    public void sendMessage(String telNum,String nickname){
        this.eventMessageService.sendOne(
            new SingleMessageSendingRequest(
                sendEventMessage(
                    telNum,
                    nickname,
                    eventNumber
                )
            )
        );
    }

}
