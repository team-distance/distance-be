package io.festival.distance.domain.admin.adminfestival.artist.entity;

import io.festival.distance.domain.admin.adminfestival.artist.dto.ArtistRequest;
import io.festival.distance.domain.admin.adminfestival.foodtruck.dto.S3Response;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artist")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "start_at")
    private String startAt;

    @Column(name = "artist_image_url")
    private String artistImageUrl;

    @Column(name = "artist_file_name")
    private String artistFileName;

    @Column(name = "school")
    private String school;

    public void update(ArtistRequest artistRequest, S3Response response) {
        this.artistName = artistRequest.artistName();
        this.startAt = artistRequest.startAt();
        this.artistImageUrl = response.imageUrl();
        this.artistFileName = response.fileName();
        this.school = artistRequest.school();
    }
}
