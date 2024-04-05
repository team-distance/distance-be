package io.festival.distance.infra.sms;

import javax.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${coolsms.api.number}")
    private String number;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    public void sendOne(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(number);
        message.setTo(to);
        message.setText("[Distance] 아래의 인증번호를 입력해주세요\n" + verificationCode);

        this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

}

