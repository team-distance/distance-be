package io.festival.distance.domain.conversation.roommember.service;

import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.conversation.roommember.service.serviceimpl.RoomMemberReader;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.infra.sse.event.ChatMessageAddedEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoomMemberProcessor {
    private final RoomMemberRepository roomMemberRepository;
    private final RoomMemberReader roomMemberReader;
    private final MemberReader memberReader;
    private final ApplicationEventPublisher aep;


    @Transactional
    public void updateRoomName(Member member, String nickName){
        List<RoomMember> allByMyRoomName = roomMemberRepository.findAllByMyRoomName(nickName);
        for (RoomMember roomMember : allByMyRoomName) {
            roomMember.updateRoomName(member.getNickName());
        }
    }

    public List<Long> saveWithdrawMemberInfo(String telNum){
        List<Long> roomNames = new ArrayList<>();
        Member member = memberReader.findTelNum(telNum);
        roomMemberReader.findRoomMemberList(member)
            .forEach(roomMember ->{
                Member opponent = memberReader.findNickName(roomMember.getMyRoomName());
                roomNames.add(opponent.getMemberId());
            });
        return roomNames;
    }

    public void createWithdrawEvent(List<Long> opponentId){
        opponentId.forEach(
            id -> aep.publishEvent(new ChatMessageAddedEvent(id))
        );
    }
}
