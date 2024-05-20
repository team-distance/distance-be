package io.festival.distance.domain.conversation.chatroom.validroomcount;

import static io.festival.distance.global.exception.ErrorCode.TOO_MANY_MY_CHATROOM;
import static io.festival.distance.global.exception.ErrorCode.TOO_MANY_OPPONENT_CHATROOM;

import io.festival.distance.domain.conversation.waiting.service.ChatWaitingService;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.global.exception.DistanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidRoomCount {
    private final ValidMyRoomCount validMyRoomCount;
    private final ValidOpponentRoom validOpponentRoom;
    private final ChatWaitingService chatWaitingService;

    public void checkRoom(Member opponent, Member me,boolean flag){
        if(validMyRoomCount.checkMyRoom(me)>=3L){
            throw new DistanceException(TOO_MANY_MY_CHATROOM);
        }

        if (validOpponentRoom.checkOpponentRoom(opponent) >= 3L) {
            if (flag) {
                chatWaitingService.saveWaitingRoom(opponent, me);
            }
            throw new DistanceException(TOO_MANY_OPPONENT_CHATROOM);
        }
    }
}
