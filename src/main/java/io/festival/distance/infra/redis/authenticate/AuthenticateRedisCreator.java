package io.festival.distance.infra.redis.authenticate;

import io.festival.distance.infra.redis.authenticate.AuthenticateNumber;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateRedisCreator {
    public AuthenticateNumber create(
        String telNum,
        String number,
        Long expirationTime
    ){
        return AuthenticateNumber.builder()
            .id(telNum)
            .AuthenticationNumber(number)
            .expiration(expirationTime)
            .build();
    }
}
