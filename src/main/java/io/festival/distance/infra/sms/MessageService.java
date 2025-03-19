package io.festival.distance.infra.sms;

public interface MessageService {
    void sendMessage(String telNum, String verificationCode);
}
