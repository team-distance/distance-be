package io.festival.distance.domain.christmas.entryticket.service;

import io.festival.distance.domain.christmas.entryticket.entity.EntryTicket;
import io.festival.distance.domain.christmas.entryticket.repository.EntryTicketRepository;
import io.festival.distance.domain.christmas.question.service.QuestionValidator;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.roommember.service.serviceimpl.RoomMemberReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EntryTicketSaver {
    private final EntryTicketRepository entryTicketRepository;
    private final QuestionValidator questionValidator;
    private static final Integer COMPLETE_QUESTION_COUNT = 10;
    private final RoomMemberReader roomMemberReader;
    @Transactional
    public Boolean save(ChatRoom chatRoom){
        if(questionValidator.completeQuestionCount(chatRoom) >= COMPLETE_QUESTION_COUNT){
            List<EntryTicket> ticketList = new ArrayList<>();
            roomMemberReader.findRoomMemberByChatRoom(chatRoom)
                .forEach(roomMember ->{
                    EntryTicket entryTicket = EntryTicket.builder()
                        .telNum(roomMember.member().getTelNum())
                        .chatRoom(chatRoom)
                        .build();
                    ticketList.add(entryTicket);
                });
            entryTicketRepository.saveAll(ticketList);
            return true;
        }
        return false;
    }
}
