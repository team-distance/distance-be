package io.festival.distance.domain.member.repository;

import io.festival.distance.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOneWithAuthoritiesByTelNum(String telNum);

    boolean existsBySchoolEmail(String schoolEmail);

    boolean existsByTelNum(String telNum);

    Optional<Member> findByTelNum(String telNum);
    Optional<Member> findByNickName(String nickName);

    void deleteByTelNum(String telNum);
}
