package io.festival.distance.domain.conversation.roommember.dto;

import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record RoomInfoDto(
    Member member
) {
    public static RoomInfoDto toResponse(RoomMember roomMember){
        return RoomInfoDto.builder()
            .member(roomMember.getMember())
            .build();
    }
}
