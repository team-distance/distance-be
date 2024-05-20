package io.festival.distance.auth.service;

import io.festival.distance.auth.dto.AccessTokenDto;
import io.festival.distance.auth.dto.RefreshTokenDto;
import io.festival.distance.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final TokenProvider tokenProvider;
    @Value("${jwt.secret}")
    String secret;

    public AccessTokenDto recreateAccessToken(RefreshTokenDto tokenDto) {
        log.error("token : "+tokenDto);
        if (tokenProvider.validateToken(tokenDto.refreshToken(), "REFRESH")) {//refresh 유효성 검증
            return AccessTokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(
                    tokenProvider.getAuthentication(tokenDto.refreshToken())))
                .build();
        }
        return null;
    }
}
