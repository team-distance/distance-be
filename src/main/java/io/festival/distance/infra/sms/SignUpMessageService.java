package io.festival.distance.infra.sms;

import static io.festival.distance.infra.sms.SmsUtil.getMessage;

import javax.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SignUpMessageService implements MessageService {

    private DefaultMessageService signUpMessageService;
    @Value("${coolsms.sign-up-api.key}")
    private String signUpApiKey;
    @Value("${coolsms.sign-up-api.secret}")
    private String signUpApiSecretKey;
    @Value("${coolsms.sign-up-api.number}")
    private String signUpNumber;

    @PostConstruct
    private void signUpInit() {
        this.signUpMessageService = NurigoApp.INSTANCE.initialize(
            signUpApiKey,
            signUpApiSecretKey,
            "https://api.coolsms.co.kr"
        );
    }

    @Override
    public void sendMessage(String telNum, String verificationCode) {
        signUpMessageService.sendOne(
            new SingleMessageSendingRequest(
                getMessage(
                    telNum,
                    verificationCode,
                    signUpNumber
                )
            )
        );
    }

    @Override
    public MessageType getType() {
        return MessageType.SIGNUP;
    }
}
