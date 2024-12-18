package io.festival.distance.auth.config;

import io.festival.distance.auth.jwt.JwtAccessDeniedHandler;
import io.festival.distance.auth.jwt.JwtAuthenticationEntryPoint;
import io.festival.distance.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity //기본적인 웹 보안 활성화
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // token을 사용하는 방식이기 때문에 csrf를 disable
            .csrf().disable()

            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            // enable h2-console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .addFilter(corsFilter) //@CrossOrigin(인증이 필요없는 곳에만 허용)
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .antMatchers(
                "/api/univ/send/email",
                "/api/university",
                "/api/admin/signup",
                "/ws/**", "/wss/**", "/meet/**",
                "/api/image",
                "/api/chatroom/both-agreed/",
                "/api/event-matching/users",
                "/api/event-matching/list",
                "/api/event-matching/send",
                "/api/event-matching",
                "/api/gps/matching",
                "/api/gps/distance", "/api/gps/distance/",
                "/api/member/signup",
                "/api/member/send/sms",
                "/api/member/authenticate",
                "/api/member/change/password",
                "/api/login",
                "/api/statistics/trending",
                "/h2-console/**",
                "/favicon.ico",
                "/api/performance/**",
                "/api/food-truck/**",
                "/api/truck-menu/**",
                "/api/refresh",
                "/api/notify/**",
                "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**",
                "/api/actuator/prometheus",
                "/api/presigned"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(
                new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}

