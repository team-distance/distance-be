package io.festival.distance.domain.member.service;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommunicationFacade {
    private final ChatRoomService chatRoomService;
    private final ChatRoomReader chatRoomReader;
    public MemberTelNumDto findTelNum(Member me, Member opponent, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
        if (chatRoomService.checkRoomCondition(me, opponent, chatRoom)) {
            return MemberTelNumDto.builder()
                .telNum(opponent.getTelNum())
                .build();
        }
        return null;
    }
}
