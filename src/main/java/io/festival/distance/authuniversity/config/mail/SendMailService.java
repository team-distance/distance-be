package io.festival.distance.authuniversity.config.mail;

import io.festival.distance.authuniversity.config.mail.dto.UnivMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SendMailService {

    @Value("${spring.mail.username}")
    private String mailUsername;

    private final JavaMailSender javaMailSender;
    private static final char[] CHAR_SET_PASSWORD = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static final char[] CHAR_SET_AUTHENTICATE_NUMBER = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public UnivMailDto createCertificationNumber(String userEmail) {
        String tempPassword = getTempPassword(CHAR_SET_PASSWORD);
        return UnivMailDto.builder()
            .mailAddress(userEmail)
            .title(userEmail + "님의 이메일 인증번호 입니다.")
            .message("<h1>인증번호 발급</h1>" +
                "<br/>" + userEmail + "님 " +
                "<br/>인증번호 입니다." +
                "<br/>인증번호 :   <h2>" + tempPassword + "</h2>")
            .tempPw(tempPassword)
            .build();
    }

    public static String getTempPassword(char[] charSet) {
        StringBuilder str = new StringBuilder();

        int idx = 0;
        for (int i = 0; i < 6; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }
        return str.toString();
    }

    @Async
    public void mailSend(UnivMailDto mailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setTo(mailDto.mailAddress());
        messageHelper.setFrom(mailUsername);
        messageHelper.setSubject(mailDto.title());
        messageHelper.setText(mailDto.message(), true);
        javaMailSender.send(message);
    }
}
