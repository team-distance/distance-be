package io.festival.distance.auth.controller;

import io.festival.distance.auth.dto.AccessTokenDto;
import io.festival.distance.auth.dto.LoginDto;
import io.festival.distance.auth.dto.RefreshTokenDto;
import io.festival.distance.auth.dto.TokenDto;
import io.festival.distance.auth.service.LoginAuthService;
import io.festival.distance.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private final LoginAuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(@RequestBody RefreshTokenDto tokenDto) {
        return ResponseEntity.ok(refreshTokenService.recreateAccessToken(tokenDto));
    }
}
