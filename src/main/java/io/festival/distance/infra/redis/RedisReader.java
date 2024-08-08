package io.festival.distance.infra.redis;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisReader {

    private final AuthenticateRedisRepository authenticateRedisRepository;

    public AuthenticateNumber findAuthenticateNumber(String telNum) {
        return authenticateRedisRepository.findById(telNum)
            .orElseThrow(() -> new DistanceException(ErrorCode.EXPIRATION_AUTHENTICATE_NUMBER));
    }
}
