package io.festival.distance.domain.member.repository;

import io.festival.distance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneWithAuthoritiesByTelNum(String telNum);

    boolean existsBySchoolEmail(String schoolEmail);

    boolean existsByTelNum(String telNum);

    Optional<Member> findByTelNum(String telNum);
    Member findByNickName(String nickName);

    void deleteByTelNum(String telNum);
}
