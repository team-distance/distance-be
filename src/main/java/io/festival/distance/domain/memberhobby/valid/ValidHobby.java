package io.festival.distance.domain.memberhobby.valid;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ValidHobby {
	public void notNullHobby(List<MemberHobbyDto> hobbies){
		if(Objects.isNull(hobbies))
			throw new DistanceException(ErrorCode.NOT_NULL_HOBBY);
	}
}
