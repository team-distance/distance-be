package io.festival.distance.domain.member.validsignup;

import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ValidEmail {
	private final MemberRepository memberRepository;
	private static final Pattern EMAIL_PATTERN =
		Pattern.compile("^[a-zA-Z0-9._%+-]{2,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	public void checkValidEmail(String email) {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new DistanceException(ErrorCode.INVALID_EMAIL_FORMAT);
		}
		if (memberRepository.existsBySchoolEmail(email)) {
			throw new DistanceException(ErrorCode.EXIST_EMAIL);
		}
	}
}
