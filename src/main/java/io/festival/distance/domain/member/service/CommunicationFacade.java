package io.festival.distance.domain.member.service;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommunicationFacade {
    private final ChatRoomService chatRoomService;
    public MemberTelNumDto findTelNum(Member me, Member opponent, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findRoom(chatRoomId);
        if (chatRoomService.checkRoomCondition(me, opponent, chatRoom)) {
            return MemberTelNumDto.builder()
                .telNum(opponent.getTelNum())
                .build();
        }
        return null;
    }
}
