package io.festival.distance.domain.membertag.valid;


import static io.festival.distance.global.exception.ErrorCode.NOT_NULL_TAG;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.global.exception.DistanceException;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ValidTag {
	public void notNullTag(List<MemberTagDto> tags){
		if(Objects.isNull(tags))
			throw new DistanceException(NOT_NULL_TAG);
	}
}
