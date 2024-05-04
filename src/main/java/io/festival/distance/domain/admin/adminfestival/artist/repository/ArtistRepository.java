package io.festival.distance.domain.admin.adminfestival.artist.repository;

import io.festival.distance.domain.admin.adminfestival.artist.entity.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Long> {
    List<Artist> findAllBySchool(String school);
}
