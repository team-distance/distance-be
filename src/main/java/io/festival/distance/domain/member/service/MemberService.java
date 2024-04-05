package io.festival.distance.domain.member.service;

import static io.festival.distance.authuniversity.config.mail.SendMailService.getAuthenticateNumber;

import io.festival.distance.domain.member.dto.AccountRequestDto;
import io.festival.distance.domain.member.dto.AccountResponseDto;
import io.festival.distance.domain.member.dto.CheckAuthenticateNum;
import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.dto.TelNumRequest;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.member.validsignup.ValidInfoDto;
import io.festival.distance.domain.member.validsignup.ValidTelNum;
import io.festival.distance.domain.memberhobby.service.MemberHobbyService;
import io.festival.distance.domain.membertag.service.MemberTagService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import io.festival.distance.infra.sms.SmsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final ValidTelNum validLoginId;
    private final ValidInfoDto validInfoDto;
    private final MemberTagService memberTagService;
    private final MemberHobbyService memberHobbyService;
    private final SmsUtil smsUtil;
    private static final String PREFIX = "#";

    /**
     * NOTE
     * 회원가입
     * 중복된 이메일인지 확인
     * 중복된 아이디인지 확인
     */
    @Transactional
    public Long createMember(MemberSignDto signDto) {

        Member member = Member.builder()
            .password(encoder.encode(signDto.password()))
            .gender(signDto.gender())
            .telNum(signDto.telNum())
            .authority(Authority.ROLE_USER)
            .mbti(signDto.mbti())
            .memberCharacter(signDto.memberCharacter())
            .nickName(signDto.department())
            .reportCount(0)
            .authUniv(UnivCert.FAILED_1)
            .activated(true)
            .build();

        validLoginId.duplicateCheckTelNum(signDto.telNum());
        validInfoDto.checkInfoDto(signDto); //hobby, tag NN 검사
        memberHobbyService.updateHobby(member, signDto.memberHobbyDto());
        memberTagService.updateTag(member, signDto.memberTagDto());
        Long memberId = memberRepository.save(member).getMemberId();
        member.memberNicknameUpdate(member.getNickName() + PREFIX + memberId);
        return member.getMemberId();
    }


    /**
     * NOTE
     * 회원 PK값으로 DB에서 삭제 -> 회원탈퇴
     */
    @Transactional
    public String withDrawal(String telNum) {
        memberRepository.existsByTelNum(telNum);
        return telNum;
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public Member findByTelNum(String telNum) {
        return memberRepository.findByTelNum(telNum)
            .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));
    }

    @Transactional
    public Long modifyAccount(String telNum, AccountRequestDto accountRequestDto) {
        Member member = findByTelNum(telNum);
        String encryptedPassword = encoder.encode(accountRequestDto.password());
        member.memberAccountModify(encryptedPassword);
        return member.getMemberId();
    }

    @Transactional(readOnly = true)
    public MemberInfoDto memberProfile(String telNum) { //멤버 프로필 조회
        Member member = findByTelNum(telNum);
        List<MemberHobbyDto> hobbyDtoList = memberHobbyService.showHobby(member);
        List<MemberTagDto> tagDtoList = memberTagService.showTag(member);
        return MemberInfoDto.builder()
            .memberCharacter(member.getMemberCharacter())
            .mbti(member.getMbti())
            .department(member.getDepartment())
            .memberTagDto(tagDtoList)
            .memberHobbyDto(hobbyDtoList)
            .build();
    }

    public Long modifyProfile(String loginId, MemberInfoDto memberInfoDto) { // 사용자가 입력한 값이 들어있음
        Member member = findByTelNum(loginId);
        member.memberInfoUpdate(memberInfoDto); //mbti랑 멤버 캐릭터 이미지 수정
        memberTagService.modifyTag(member, memberInfoDto.memberTagDto());
        memberHobbyService.modifyHobby(member, memberInfoDto.memberHobbyDto());
        return member.getMemberId();
    }

    @Transactional(readOnly = true)
    public MemberTelNumDto findTelNum(Long memberId) {
        Member member = findMember(memberId);
        return MemberTelNumDto.builder()
            .telNum(member.getTelNum())
            .build();
    }

    @Transactional
    public void increaseDeclare(Long memberId) {
        Member member = findMember(memberId);
        member.updateReport();
    }

    @Transactional
    public void blockAccount(Long opponentId) {
        Member member = findMember(opponentId);
        member.disableAccount();
    }


    /**
     * NOTE
     * 인증메일 전송
     * @param telNumRequest 전화번호
     */
    public String sendSms(TelNumRequest telNumRequest) {
        String num = getAuthenticateNumber();
        smsUtil.sendOne(telNumRequest.telNum(), num);
        return num;
    }

    public void verifyAuthenticateNum(CheckAuthenticateNum checkAuthenticateNum,
        String authenticateNum) {
        if (!authenticateNum.equals(checkAuthenticateNum.authenticateNum())) {
            throw new IllegalStateException("인증번호가 일치하지 않습니다!");
        }
    }

    public String verifyUniv(String name) {
        return findByTelNum(name).getAuthUniv().getType();
    }
}
