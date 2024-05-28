package io.festival.distance.domain.ip.entity;

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
@Table(name = "member_ip")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_ip_id")
    private Long memberIpId;

    @Column(name = "member_ip_addr")
    private String memberIpAddr;

    @Column(name = "ip_count")
    private Integer ipCount;

    public void update(){
        this.ipCount+=1;
    }
}
