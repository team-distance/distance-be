package io.festival.distance.auth.service;

import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.auth.dto.LoginDto;
import io.festival.distance.auth.dto.TokenDto;
import io.festival.distance.auth.jwt.TokenProvider;
import io.festival.distance.auth.refresh.Refresh;
import io.festival.distance.auth.refresh.RefreshRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class LoginAuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        try {
            Member member = memberRepository.findByTelNum(loginDto.getTelNum())
                .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));

            Authentication authentication = getAuthentication(loginDto);
            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            if (refreshRepository.existsBySubject(loginDto.getTelNum())) {
                refreshRepository.deleteBySubject(loginDto.getTelNum());
            }

            Refresh refresh = Refresh.builder()
                .refreshToken(refreshToken)
                .subject(loginDto.getTelNum())
                .build();
            refreshRepository.save(refresh);

            member.clientTokenUpdate(loginDto.getClientToken()); // FCM clientToken 갱신
            return new TokenDto(accessToken, refreshToken);
        }catch (BadCredentialsException e) {
            throw new DistanceException(ErrorCode.NOT_CORRECT_PASSWORD);
        }

    }

    public Authentication getAuthentication(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getTelNum(), loginDto.getPassword());
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
        return authentication;
    }
}
