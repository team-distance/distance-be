package io.festival.distance.infra.sms;

import io.festival.distance.domain.member.dto.TelNumRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFactory {
    private final MessageSender messageSender;

    public void sendOne(TelNumRequest telNumRequest, String verificationCode) {
        MessageService messageService = messageSender.getMessageService(telNumRequest.type());
        messageService.sendMessage(telNumRequest.telNum(), verificationCode);
    }
}
