package io.festival.distance.infra.redis.authenticate;

import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import io.festival.distance.infra.redis.authenticate.AuthenticateNumber;
import io.festival.distance.infra.redis.authenticate.AuthenticateRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateRedisReader {

    private final AuthenticateRedisRepository authenticateRedisRepository;

    public AuthenticateNumber findAuthenticateNumber(String telNum) {
        return authenticateRedisRepository.findById(telNum)
            .orElseThrow(() -> new DistanceException(ErrorCode.EXPIRATION_AUTHENTICATE_NUMBER));
    }
}
