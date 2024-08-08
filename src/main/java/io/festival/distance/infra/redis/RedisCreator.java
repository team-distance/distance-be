package io.festival.distance.infra.redis;

import org.springframework.stereotype.Component;

@Component
public class RedisCreator {
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
