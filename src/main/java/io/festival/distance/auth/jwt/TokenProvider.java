package io.festival.distance.auth.jwt;

import io.festival.distance.auth.refresh.RefreshRepository;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.Instant;

import static io.festival.distance.auth.utils.SecurityMessage.*;


@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String REFRESH_TYPE = "refresh";
    private static final String ACCESS_TYPE = "access";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenExpireTime;
    private Key key;
    private final RefreshRepository refreshRepository;

    public TokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
        @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenExpireTime,
        RefreshRepository refreshRepository) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000; //30분 60*30
        this.refreshTokenExpireTime = refreshTokenExpireTime * 1000; //1주일로 설정
        this.refreshRepository=refreshRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        // 토큰의 expire 시간을 설정
        Date date=new Date();
        Date validity = new Date(date.getTime() + this.tokenValidityInMilliseconds);

        return Jwts.builder()
            .setSubject(authentication.getName()) //Payload에 유저 네임 저장
            .setIssuer("DISTANCE") //토큰 발급자 iss 지정
            .setIssuedAt(DateTimeUtils.toDate(Instant.now()))
            .claim(AUTHORITIES_KEY, authorities) // 정보 저장
            .claim("type",ACCESS_TYPE)
            .signWith(key, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
            .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
            .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        // 토큰의 expire 시간을 설정
        //long now = (new Date()).getTime();
        Date date=new Date();
        Date validity = new Date(date.getTime() + this.refreshTokenExpireTime);

        return Jwts.builder()
            .setSubject(authentication.getName()) //Payload에 유저 네임 저장
            .setIssuer("DISTANCE") //토큰 발급자 iss 지정
            .setIssuedAt(date)
            .claim(AUTHORITIES_KEY, authorities) // 정보 저장
            .claim("type", REFRESH_TYPE) // 정보 저장
            .signWith(key, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
            .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
            .compact();
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token,String type) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error(MALFORMED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error(UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.error(WRONG_JWT);
        } catch (ExpiredJwtException e) {
            log.error(EXPIRED_JWT);
            if(type.equals("REFRESH")){
                log.debug(token);
                refreshRepository.deleteByRefreshToken(token);
                log.debug("refresh token delete success!!");
            }
            throw new DistanceException(ErrorCode.EXPIRED_JWT);
        }
        return false;
    }
}
