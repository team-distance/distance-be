package io.festival.distance.auth.refresh;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh,Long> {
    Optional<Refresh> findBySubject(String subject);
}
