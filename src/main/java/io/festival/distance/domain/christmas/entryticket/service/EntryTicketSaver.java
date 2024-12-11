package io.festival.distance.domain.christmas.entryticket.service;

import io.festival.distance.domain.christmas.entryticket.entity.EntryTicket;
import io.festival.distance.domain.christmas.entryticket.repository.EntryTicketRepository;
import io.festival.distance.domain.christmas.question.service.QuestionValidator;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EntryTicketSaver {
    private final EntryTicketRepository entryTicketRepository;
    private final QuestionValidator questionValidator;
    private static final Integer COMPLETE_QUESTION_COUNT = 10;

    @Transactional
    public Boolean save(ChatRoom chatRoom, String telNum){
        if(questionValidator.completeQuestionCount(chatRoom) >= COMPLETE_QUESTION_COUNT){
            EntryTicket entryTicket = EntryTicket.builder()
                .telNum(telNum)
                .chatRoom(chatRoom)
                .build();
            entryTicketRepository.save(entryTicket);
            return true;
        }
        return false;
    }
}
