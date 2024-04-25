package io.festival.distance.domain.conversation.chatroom.service;

import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomInfoDto> findAllRoom(String telNum) {
        Member member = memberRepository.findByTelNum(telNum) //현재 로그인한 객체
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));

        return roomMemberRepository.findAllByMember(member)
            .stream()
            .map(roomMember -> {
                ChatRoom chatRoom = roomMember.getChatRoom();

                Optional<Member> opponent = memberRepository.findByNickName(
                    roomMember.getMyRoomName());
                return opponent.map(
                    //멤버가 존재하는 경우
                    opponentMember -> {
                        ChatMessage message = chatMessageRepository.findTop1ByChatRoomOrderByCreateDtDesc(
                            chatRoom); //가장 최근 메시지 불러옴

                        String lastMessage =
                            Objects.isNull(message) ? "새로운 채팅방이 생성되었습니다!"
                                : message.getChatMessage();
                        LocalDateTime createDt =
                            Objects.isNull(message) ? LocalDateTime.now()
                                : message.getCreateDt();
                        Integer count = chatMessageRepository.countByChatRoomAndChatMessageIdGreaterThan(
                            chatRoom, roomMember.getLastReadMessageId());

                        return ChatRoomInfoDto.builder()
                            .chatRoomId(chatRoom.getChatRoomId())
                            .roomName(roomMember.getMyRoomName())
                            .createDt(roomMember.getCreateDt())
                            .modifyDt(createDt)
                            .opponentMemberId(opponentMember.getMemberId())
                            .memberCharacter(opponentMember.getMemberCharacter())
                            .lastMessage(lastMessage)
                            .askedCount(count)
                            .build();
                    }).orElseGet(() -> {
                    String message = "상대방이 탈퇴했습니다.";
                    return ChatRoomInfoDto.builder()
                        .chatRoomId(chatRoom.getChatRoomId())
                        .roomName(roomMember.getMyRoomName())
                        .createDt(roomMember.getCreateDt())
                        .lastMessage(message)
                        .build();
                });
            }).collect(Collectors.toList());
    }

    @Transactional
    public void addRoomMember(Long chatRoomId, List<Member> member) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 방"));

        for (Member me : member) {
            Member opponent = member //상대방 찾음
                .stream()
                .filter(o -> !o.getMemberId().equals(me.getMemberId()))
                .findFirst()
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));

            saveRoomMember(me, chatRoom, opponent);
        }
    }

    public void saveRoomMember(Member me, ChatRoom chatRoom, Member opponent) {
        RoomMember roomMember = RoomMember.builder()
            .chatRoom(chatRoom)
            .myRoomName(opponent.getNickName())
            .lastReadMessageId(0L)
            .member(me)
            .build();
        roomMemberRepository.save(roomMember);
    }

    @Transactional
    public void delete(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public ChatRoom findRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 방"));
    }

    @Transactional(readOnly = true)
    public Boolean getAgreedStatus(Long chatRoomId) {
        return findRoom(chatRoomId).isBothAgreed();
    }

    public boolean checkRoomCondition(Member me, Member opponent, ChatRoom chatRoom) {
        return (roomMemberRepository.existsByChatRoomAndMemberAndMyRoomName(chatRoom, opponent,
            me.getNickName()) && chatRoom.isBothAgreed());
    }

    @Transactional
    public void setAgreed(ChatRoom chatRoom) {
        chatRoom.updateAgreed();
    }
}
