package io.festival.distance.domain.firebase.service.serviceimpl;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmMessageCreator {

    public Message createMessage(
        MemberFcmDto memberFcmDto,
        WebpushNotification webpushNotification
    ){
        return Message.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(webpushNotification)
                .build())
            .setToken(memberFcmDto.member().getClientToken())
            .build();
    }

    public MulticastMessage createMultiCastMessage(
        List<String> tokens,
        WebpushNotification webpushNotification
    ){
        return MulticastMessage.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(webpushNotification)
                .build())
            .addAllTokens(tokens)
            .build();
    }
}
