package io.festival.distance.domain.conversation.waiting.service;

import static io.festival.distance.domain.firebase.service.FCMService.ADD_WAITING_ROOM_MESSAGE;
import static io.festival.distance.domain.firebase.service.FCMService.SET_SENDER_NAME;

import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingCountDto;
import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingDto;
import io.festival.distance.domain.conversation.waiting.entity.ChatWaiting;
import io.festival.distance.domain.conversation.waiting.repository.ChatWaitingRepository;
import io.festival.distance.domain.firebase.dto.MemberFcmDto;
import io.festival.distance.domain.firebase.service.FCMService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatWaitingService {
    private final ChatWaitingRepository chatWaitingRepository;
    private final MemberService memberService;
    private final FCMService fcmService;
    private final MemberRepository  memberRepository;
    private final ApplicationEventPublisher aep;
    /**
     * 대기열 증가하는 로직 -> 그럼 여기다가 eventListener을 달면?
     * @param opponent
     * @param me
     */
    @Transactional
    public void saveWaitingRoom(Member opponent, Member me) {
        if(!chatWaitingRepository.existsByLoveSenderAndLoveReceiver(me,opponent)){
            ChatWaiting chatWaiting = ChatWaiting.builder()
                .loveReceiver(opponent) //상대방
                .loveSender(me) //내가 좋아요
                .myRoomName(me.getNickName())
                .build();
            Long waitingId = chatWaitingRepository.save(chatWaiting).getWaitingId();
            System.out.println("waitingId = " + waitingId);

            MemberFcmDto dto = MemberFcmDto.builder()
                .senderNickName(SET_SENDER_NAME)
                .message(ADD_WAITING_ROOM_MESSAGE)
                .member(opponent)
                .build();

            fcmService.saveFcm(dto);
            //aep.publishEvent(new ChatWaitingAddedEvent(opponent.getMemberId()));
            //aep.publishEvent(new ChatWaitingAddedEvent(me.getMemberId()));
        }
    }

    @Transactional(readOnly = true)
    public List<ChatWaitingDto> getWaitingRoom(String loginId) {
        Member member = memberService.findByTelNum(loginId); //나
        List<ChatWaiting> allByLoveReceiver = chatWaitingRepository.findAllByLoveReceiver(member);
        List<ChatWaitingDto> chatWaitingDtoList = new ArrayList<>();
        for (ChatWaiting chatWaiting : allByLoveReceiver) {
            Member opponent = memberService.findMember(chatWaiting.getLoveSender().getMemberId());
            ChatWaitingDto dto = ChatWaitingDto.builder()
                    .loveReceiverId(chatWaiting.getLoveReceiver().getMemberId()) //나
                    .loveSenderId(chatWaiting.getLoveSender().getMemberId()) //상대방
                    .myRoomName(chatWaiting.getMyRoomName())
                    .waitingRoomId(chatWaiting.getWaitingId())
                    .memberCharacter(opponent.getMemberCharacter())
                    .build();
            chatWaitingDtoList.add(dto);
        }
        return chatWaitingDtoList;
    }

    @Transactional(readOnly = true)
    public ChatWaitingCountDto countingWaitingRoom(Long loginId) {
        Member member = memberRepository.findById(loginId)
            .orElseThrow(()-> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
        Integer count = chatWaitingRepository.countByLoveReceiver(member);

        return ChatWaitingCountDto.builder()
                .waitingCount(count)
                .build();
    }

    @Transactional
    public void deleteRoom(Long waitingRoodId,String loginId) {
        Member member = memberService.findByTelNum(loginId);
        chatWaitingRepository.deleteByWaitingIdAndLoveReceiver(waitingRoodId,member);
    }
}