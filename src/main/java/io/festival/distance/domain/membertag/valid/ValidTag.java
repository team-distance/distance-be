package io.festival.distance.domain.membertag.valid;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ValidTag {
	public void notNullTag(List<MemberTagDto> tags){
		if(Objects.isNull(tags))
			throw new DistanceException(ErrorCode.NOT_NULL_TAG);
	}
}
