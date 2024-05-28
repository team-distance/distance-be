package io.festival.distance.domain.ip.repository;

import io.festival.distance.domain.ip.entity.MemberIp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberIpRepository extends JpaRepository<MemberIp,Long> {
    @Query("select m.ipCount from MemberIp m where m.memberIpAddr =:memberIpAddr")
    Integer findIpCount(@Param(value = "memberIpAddr") String memberIpAddr);

    boolean existsByMemberIpAddr(String memberIpAddr);
    Optional<MemberIp> findByMemberIpAddr(String memberIpAddr);

}
