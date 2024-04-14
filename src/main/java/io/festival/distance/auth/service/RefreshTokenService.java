package io.festival.distance.auth.service;

import io.festival.distance.auth.dto.AccessTokenDto;
import io.festival.distance.auth.dto.TokenDto;
import io.festival.distance.auth.jwt.TokenProvider;
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

    public AccessTokenDto recreateAccessToken(TokenDto tokenDto) {
        String accessClaims = tokenDecoder(tokenDto.getAccessToken());
        String refreshClaims = tokenDecoder(tokenDto.getRefreshToken());
        if (accessClaims.equals(refreshClaims)) {
            if (tokenProvider.validateToken(tokenDto.getRefreshToken())) {//refresh 유효성 검증
                return AccessTokenDto.builder()
                    .accessToken(tokenProvider.createAccessToken(
                        tokenProvider.getAuthentication(tokenDto.getRefreshToken())))
                    .build();
            }
        }
        return null;
    }

    private String tokenDecoder(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}