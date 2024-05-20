package io.festival.distance.auth.refresh;

import io.festival.distance.global.exception.DistanceException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<Refresh,Long> {
    void deleteBySubject(String subject);
    boolean existsBySubject(String subject);

    @Transactional(noRollbackFor = ExpiredJwtException.class)
    void deleteByRefreshToken(String refreshToken);
}
