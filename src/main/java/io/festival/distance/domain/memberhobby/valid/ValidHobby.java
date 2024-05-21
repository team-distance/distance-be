package io.festival.distance.domain.memberhobby.valid;


import static io.festival.distance.global.exception.ErrorCode.NOT_NULL_HOBBY;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.global.exception.DistanceException;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ValidHobby {
	public void notNullHobby(List<MemberHobbyDto> hobbies){
		if(Objects.isNull(hobbies))
			throw new DistanceException(NOT_NULL_HOBBY);
	}
}
