package io.festival.distance.domain.studentcard.dto;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.studentcard.entity.StudentCard;
import java.util.Base64;
import lombok.Builder;
import lombok.Builder.ObtainVia;

@Builder
public record ImageResponse(
    Long imageId,
    Long memberId,
    String imageData
) {
    public static ImageResponse toEntity(StudentCard studentCard){
        return ImageResponse.builder()
            .imageId(studentCard.getStudentCardId())
            .memberId(studentCard.getMember().getMemberId())
            .imageData(Base64.getEncoder().encodeToString(studentCard.getImageData()))
            .build();
    }
}
