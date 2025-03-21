package io.festival.distance.domain.member.entity;

import static javax.persistence.EnumType.STRING;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "school_email")
    private String schoolEmail;

    @Column(name = "tel_num")
    private String telNum;

    @Column(name = "encrypted_password")
    private String password;

    @Column(name = "mbti")
    private String mbti;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "school")
    private String school;

    @Column(name = "college")
    private String college;

    @Column(name = "department")
    private String department;

    @Column(name = "member_image")
    private String memberCharacter;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "client_token")
    private String clientToken;

    @Column(name = "report_count")
    private Integer reportCount;

    @Column(name = "room_count")
    private Integer roomCount;

    @Enumerated(STRING)
    @Column(name = "auth_univ")
    private UnivCert authUniv;

    @Column(name = "authority")
    private String authority;

    public void memberInfoUpdate(MemberInfoDto memberInfoDto) {
        this.mbti = memberInfoDto.mbti();
        this.memberCharacter = memberInfoDto.memberCharacter();
    }
    public void memberGpsUpdate(GpsDto gpsDto) {
        this.latitude = gpsDto.latitude();
        this.longitude = gpsDto.longitude();
    }
    public void updateAuthUniv(UnivCert univCert){
        this.authUniv= univCert;
    }
    public void memberNicknameUpdate(String nickName) {
        this.nickName = nickName;
    }
    public void memberAccountModify(String encrypted_password) {
        this.password = encrypted_password;
    }
    public void clientTokenUpdate(String clientToken) {
        this.clientToken = clientToken;
    }
    public void increaseReport() {
        this.reportCount += 1;
    }
    public void disableAccount() {
        this.activated = false;
    }

    public void clearInfo(){
        this.clientToken=null;
    }

    public void updateEmail(String schoolEmail) {
        this.schoolEmail=schoolEmail;
    }

    public void updateRoomCount() {
        if(this.roomCount<5)
            this.roomCount++;
    }
}
