package io.festival.distance.domain.firebase.service.serviceimpl;

import com.google.firebase.messaging.WebpushNotification;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import org.springframework.stereotype.Component;

@Component
public class FcmWebPushCreator {
    public WebpushNotification create(MemberFcmDto memberFcmDto){
        return WebpushNotification.builder()
            .setTitle(memberFcmDto.senderNickName())
            .setBody(memberFcmDto.message())
            .setImage(
                "https://s3.ap-northeast-2.amazonaws.com/9oorm.distance/icons/apple-touch-icon-72x72.png")
            .build();
    }
}
