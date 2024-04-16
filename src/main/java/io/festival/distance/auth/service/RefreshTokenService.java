package io.festival.distance.auth.service;

import io.festival.distance.auth.dto.AccessTokenDto;
import io.festival.distance.auth.dto.RefreshTokenDto;
import io.festival.distance.auth.dto.TokenDto;
import io.festival.distance.auth.jwt.TokenProvider;
import io.festival.distance.auth.refresh.RefreshRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final TokenProvider tokenProvider;
    @Value("${jwt.secret}")
    String secret;

    public AccessTokenDto recreateAccessToken(RefreshTokenDto tokenDto) {
        if (tokenProvider.validateToken(tokenDto.refreshToken(), "REFRESH")) {//refresh 유효성 검증
            return AccessTokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(
                    tokenProvider.getAuthentication(tokenDto.refreshToken())))
                .build();
        }
        return null;
    }
}
