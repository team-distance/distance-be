package io.festival.distance.domain.firebase.service.serviceimpl;

import io.festival.distance.domain.firebase.entity.Fcm;
import org.springframework.stereotype.Component;

@Component
public class FcmUpdater {
    public void update(Fcm fcm){
        fcm.updateFcm();
    }
}
