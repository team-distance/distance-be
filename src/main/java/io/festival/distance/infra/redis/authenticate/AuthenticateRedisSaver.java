package io.festival.distance.infra.redis.authenticate;

import io.festival.distance.infra.redis.authenticate.AuthenticateNumber;
import io.festival.distance.infra.redis.authenticate.AuthenticateRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateRedisSaver {
    private final AuthenticateRedisRepository authenticateRedisRepository;

    public void save(AuthenticateNumber authenticateNumber){
        authenticateRedisRepository.save(authenticateNumber);
    }
}
