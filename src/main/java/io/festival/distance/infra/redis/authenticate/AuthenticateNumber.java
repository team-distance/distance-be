package io.festival.distance.infra.redis.authenticate;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("authentication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthenticateNumber {
    @Id
    private String id;
    private String AuthenticationNumber;
    @TimeToLive
    private Long expiration;
}
