package io.festival.distance.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSaver {
    private final AuthenticateRedisRepository authenticateRedisRepository;

    public void save(AuthenticateNumber authenticateNumber){
        authenticateRedisRepository.save(authenticateNumber);
    }
}
