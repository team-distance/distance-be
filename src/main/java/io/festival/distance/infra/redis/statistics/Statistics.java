package io.festival.distance.infra.redis.statistics;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("statistics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Statistics {
    @Id
    private Long id;
    private Integer count;
    @TimeToLive
    private Long expiration;

    public void update(){
        this.count++;
    }
}
