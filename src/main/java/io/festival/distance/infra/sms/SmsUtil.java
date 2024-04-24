package io.festival.distance.infra.sms;

import io.festival.distance.domain.member.dto.TelNumRequest;
import javax.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    //비번 찾기용
    @Value("${coolsms.find-api.key}")
    private String findApiKey;
    @Value("${coolsms.find-api.secret}")
    private String findApiSecretKey;
    @Value("${coolsms.find-api.number}")
    private String findNumber;

    // 회원가입 용
    @Value("${coolsms.sign-up-api.key}")
    private String signUpApiKey;
    @Value("${coolsms.sign-up-api.secret}")
    private String signUpApiSecretKey;
    @Value("${coolsms.sign-up-api.number}")
    private String signUpNumber;

    private DefaultMessageService findMessageService;
    private DefaultMessageService signUpMessageService;

    @PostConstruct
    private void findInit() {
        this.findMessageService = NurigoApp.INSTANCE.initialize(findApiKey, findApiSecretKey,
            "https://api.coolsms.co.kr");
    }

    @PostConstruct
    private void signUpInit() {
        this.signUpMessageService = NurigoApp.INSTANCE.initialize(signUpApiKey, signUpApiSecretKey,
            "https://api.coolsms.co.kr");
    }

    public void sendOne(TelNumRequest telNumRequest, String verificationCode) {
        switch (telNumRequest.type()) {
            case "SIGNUP": {
                Message message = getMessage(telNumRequest.telNum(), verificationCode,signUpNumber);
                this.signUpMessageService.sendOne(new SingleMessageSendingRequest(message));
                break;
            }
            case "FIND": {
                Message message = getMessage(telNumRequest.telNum(), verificationCode,findNumber);
                this.findMessageService.sendOne(new SingleMessageSendingRequest(message));
                break;
            }
        }
    }

    @NotNull
    private Message getMessage(String to, String verificationCode,String from) {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText("[Distance] \n"
            + "인증번호 [" + verificationCode + "] 를 입력해주세요");
        return message;
    }

}

