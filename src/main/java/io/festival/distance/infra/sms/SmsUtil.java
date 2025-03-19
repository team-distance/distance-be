package io.festival.distance.infra.sms;

import net.nurigo.sdk.message.model.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    @NotNull
    public static Message getMessage(String to, String verificationCode, String from) {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText("[Distance] \n"
            + "인증번호 [" + verificationCode + "] 를 입력해주세요");
        return message;
    }


    public static Message sendEventMessage(String telNum,String nickname, String from){
        Message message = new Message();
        message.setFrom(from);
        message.setTo(telNum);
        message.setText("[Distance] \n"
            + nickname+"님과 매칭되었어요.\n"
            + "링크를 클릭해 대화를 시작해보세요!\n"
            + "https://dis-tance.com/event-matching");
        return message;
    }
}

