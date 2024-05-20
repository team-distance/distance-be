package io.festival.distance.domain.member.validsignup;

import static io.festival.distance.global.exception.ErrorCode.EXIST_EMAIL;
import static io.festival.distance.global.exception.ErrorCode.INVALID_EMAIL_FORMAT;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.global.exception.DistanceException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidEmail {
	private final MemberRepository memberRepository;
	private static final Pattern EMAIL_PATTERN =
		Pattern.compile("^[a-zA-Z0-9._%+-]{2,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	public void checkValidEmail(String email) {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new DistanceException(INVALID_EMAIL_FORMAT);
		}
		if (memberRepository.existsBySchoolEmail(email)) {
			throw new DistanceException(EXIST_EMAIL);
		}
	}
}
