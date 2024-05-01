package io.festival.distance.domain.conversation.chatroom.service;

import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.chatroom.validroomcount.ValidExistRoom;
import io.festival.distance.domain.conversation.chatroom.validroomcount.ValidRoomCount;
import io.festival.distance.domain.conversation.waiting.entity.ChatWaiting;
import io.festival.distance.domain.conversation.waiting.repository.ChatWaitingRepository;
import io.festival.distance.domain.gps.service.GpsProcessor;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.validlogin.ValidUnivCert;
import io.festival.distance.exception.ChatRoomException;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatFacadeService {

    private final ChatRoomService chatRoomService;
    private final ValidRoomCount validRoomCount;
    private final ValidExistRoom validExistRoom;
    private final ValidUnivCert validUnivCert;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatWaitingRepository chatWaitingRepository;
    private final GpsProcessor gpsProcessor;

    private static final String ACTIVE="ACTIVE";

    @Transactional(noRollbackFor = ChatRoomException.class)
    public Long generateRoom(ChatRoomDto chatRoomDto, Principal principal, boolean flag) {
        Member opponent = memberRepository.findById(chatRoomDto.getMemberId())
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER)); //상대방 7

        Member me = memberRepository.findByTelNum(principal.getName())
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER)); //나 2

        validUnivCert.checkUnivCert(me);

        //validGps.checkGps(me);

        //기존에 대화 중인 방이 있는 경우 해당 방id 반환 -> 둘 다 방에 있는 경우
        if (validExistRoom.ExistRoom(me, opponent).isPresent()) {
            return validExistRoom.ExistRoom(me, opponent).get();
        }

        // A, B 대화 중 A가 나갔다가 다시 B랑 대화했던 방으로 들어오는 경우 -> 나갔던 사람이 다시 들어오는 경우
        Optional<Long> reEnterRoomId = validExistRoom.ReEnterRoom(me, opponent);

        Long opponentRoomId = validExistRoom.existOpponentChatRoom(reEnterRoomId, me, opponent);
        if (opponentRoomId != null) {
            return opponentRoomId;
        }

        Optional<Long> reEnterRoomId1 = validExistRoom.ReEnterInActiveRoom(me, opponent);
        if (reEnterRoomId1.isPresent()){
            return reEnterRoomId1.get();
        }
        //이미 방에 있는데

        validRoomCount.checkRoom(opponent, me, flag);

        ChatRoom chatRoom = ChatRoom.builder()
            .roomName(opponent.getNickName())
            .roomStatus(ACTIVE)
            .distance(gpsProcessor.getDistance(me.getMemberId(), opponent.getMemberId()))
            .bothAgreed(false)
            .build();

        Long chatRoomId = chatRoomRepository.save(chatRoom).getChatRoomId();
        chatRoomService.addRoomMember(chatRoomId, List.of(opponent, me));
        return chatRoomId;
    }

    @Transactional
    public Long approveRoom(Long waitingRoomId, Principal principal) {
        ChatWaiting chatWaiting = chatWaitingRepository.findById(waitingRoomId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_WAITING_ROOM));

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
            .memberId(chatWaiting.getLoveSender().getMemberId())
            .build();

        Long generateRoomId = generateRoom(chatRoomDto, principal, false);
        chatWaitingRepository.deleteById(waitingRoomId);
        return generateRoomId;
    }
}