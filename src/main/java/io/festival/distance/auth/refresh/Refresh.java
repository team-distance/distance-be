package io.festival.distance.auth.refresh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_id")
    private Long refreshId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "subject")
    private String subject;
}
