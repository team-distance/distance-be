package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.memberhobby.valid.ValidHobby;
import io.festival.distance.domain.membertag.valid.ValidTag;
import org.springframework.stereotype.Component;

import io.festival.distance.domain.member.dto.MemberSignDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidInfoDto {
	private final ValidHobby validHobby;
	private final ValidTag validTag;
	public void checkInfoDto(MemberSignDto signDto) {
		validHobby.notNullHobby(signDto.memberHobbyDto());
		validTag.notNullTag(signDto.memberTagDto());
	}
}
