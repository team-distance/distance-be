package io.festival.distance.domain.member.repository;

import io.festival.distance.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneWithAuthoritiesByTelNum(String telNum);

    boolean existsBySchoolEmail(String schoolEmail);

    boolean existsByTelNum(String telNum);

    Optional<Member> findByTelNum(String telNum);

    Optional<Member> findByNickName(String nickName);

    void deleteByTelNum(String telNum);

    List<Member> findAllBySchoolAndGender(String school, String gender);

    List<Member> findByModifyDtAfter(LocalDateTime time);

    @Query("select m from Member m "
        + "where m.school = :school and "
        + "m.memberCharacter = :character and "
        + "m.gender <> :gender"
    )
    List<Member> findAllBySchoolAndMemberCharacter(
        @Param(value = "school") String school,
        @Param(value = "character") String character,
        @Param(value = "gender") String gender
    );

    @Query(value = """
    SELECT * 
    FROM member
    WHERE ST_CONTAINS(
            ST_Buffer(
                ST_GeomFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')'), 4326), 
                :radius
            ),
            location 
          )
""", nativeQuery = true)
    List<Member> findNearbyMembers(
        @Param("latitude") Double latitude,
        @Param("longitude") Double longitude,
        @Param("radius") Double radius
    );



}
