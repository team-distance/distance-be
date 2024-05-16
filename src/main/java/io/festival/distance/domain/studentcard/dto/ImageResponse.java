package io.festival.distance.domain.studentcard.dto;

import io.festival.distance.domain.studentcard.entity.StudentCard;
import java.util.Base64;
import lombok.Builder;

@Builder
public record ImageResponse(
    Long imageId,
    Long memberId,//학교, 성별, 학과
    String school,
    String department,
    String gender,
    String imageData
) {
    public static ImageResponse toEntity(StudentCard studentCard){
        return ImageResponse.builder()
            .imageId(studentCard.getStudentCardId())
            .memberId(studentCard.getMember().getMemberId())
            .imageData(Base64.getEncoder().encodeToString(studentCard.getImageData()))
            .school(studentCard.getMember().getSchool())
            .department(studentCard.getMember().getDepartment())
            .gender(studentCard.getMember().getGender())
            .build();
    }
}
