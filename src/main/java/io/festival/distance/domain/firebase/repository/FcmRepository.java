package io.festival.distance.domain.firebase.repository;

import io.festival.distance.domain.firebase.entity.Fcm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FcmRepository extends JpaRepository<Fcm,Long> {
    @Query("select f from Fcm f where f.isSend = false and f.message =:message")
    List<Fcm> findAllByIsSend(@Param("message") String message);
}
