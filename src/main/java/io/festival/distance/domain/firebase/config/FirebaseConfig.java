package io.festival.distance.domain.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.ByteArrayInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FirebaseConfig {

    /**
     * NOTE
     * GoogleCredentials 객체를 생성 (Firebase에 접근시 서버 인증 정보)
     */
    private final FirebaseProperties properties;

    public FirebaseConfig(FirebaseProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        try {
            // FirebaseOptions 객체를 생성 (Firebase 접근시 서버 인증 정보)
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                    new ByteArrayInputStream(properties.toJson().getBytes())))
                .build();
            FirebaseApp.initializeApp(firebaseOptions); // FirebaseApp 객체 초기화
        } catch (Exception e) {
			log.info(e.getMessage());
        }
        return FirebaseMessaging.getInstance();
    }
}