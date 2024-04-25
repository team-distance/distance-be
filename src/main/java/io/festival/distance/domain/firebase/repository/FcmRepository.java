package io.festival.distance.domain.firebase.repository;

import io.festival.distance.domain.firebase.entity.Fcm;
import io.festival.distance.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FcmRepository extends JpaRepository<Fcm, Long> {

    @Query("select f from Fcm f where f.isSend = false and f.message like %:message")
    List<Fcm> SendByFcmMessage(@Param("message") String message);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END "
        + "FROM Fcm f "
        + "WHERE f.member = :member AND f.senderName = :senderName AND f.isSend = false")
    boolean existByFcmMessage(@Param("member") Member member,
        @Param("senderName") String senderName);
}
