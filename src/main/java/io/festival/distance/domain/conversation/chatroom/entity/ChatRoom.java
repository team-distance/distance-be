package io.festival.distance.domain.conversation.chatroom.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chatroom")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ChatRoom extends BaseTimeEntity {  //채팅방
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long chatRoomId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_status")
    private String roomStatus;

    @Column(name = "both_agreed")
    private boolean bothAgreed;

    @Column(name = "distance")
    private Double distance;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.REMOVE)
    private List<RoomMember> member=new ArrayList<>();

    public void roomInActive(){
        this.roomStatus="INACTIVE";
    }

    public void roomActive(){
        this.roomStatus="ACTIVE";
    }

    public void updateAgreed(){
        this.bothAgreed=true;
    }
}
