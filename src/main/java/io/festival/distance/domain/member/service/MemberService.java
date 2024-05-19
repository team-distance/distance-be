package io.festival.distance.domain.member.service;

import static io.festival.distance.authuniversity.config.mail.SendMailService.CHAR_SET_AUTHENTICATE_NUMBER;
import static io.festival.distance.authuniversity.config.mail.SendMailService.getTempPassword;
import static io.festival.distance.domain.member.exception.MemberErrorCode.NOT_CORRECT_AUTHENTICATION_NUMBER;

import io.festival.distance.auth.refresh.RefreshDeleter;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomDeleter;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberProcessor;
import io.festival.distance.domain.member.dto.CheckAuthenticateNum;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.dto.TelNumRequest;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.exception.MemberException;
import io.festival.distance.domain.member.service.serviceimpl.MemberCreator;
import io.festival.distance.domain.member.service.serviceimpl.MemberDeleter;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import io.festival.distance.domain.member.service.serviceimpl.MemberVerifier;
import io.festival.distance.domain.memberhobby.service.HobbyCreator;
import io.festival.distance.domain.memberhobby.service.HobbyUpdater;
import io.festival.distance.domain.membertag.service.TagCreator;
import io.festival.distance.domain.membertag.service.TagUpdater;
import io.festival.distance.infra.sms.SmsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberCreator memberCreator;
    private final MemberDeleter memberDeleter;
    private final MemberUpdater memberUpdater;
    private final MemberReader memberReader;
    private final MemberVerifier memberVerifier;

    private final HobbyCreator hobbyCreator;
    private final HobbyUpdater hobbyUpdater;

    private final TagCreator tagCreator;
    private final TagUpdater tagUpdater;

    private final ChatRoomDeleter chatRoomDeleter;

    private final RefreshDeleter refreshDeleter;
    private final CommunicationFacade communicationFacade;
    private final SmsUtil smsUtil;
    private final RoomMemberProcessor roomMemberProcessor;

    /**
     * NOTE
     * 회원가입
     */
    @Transactional
    public Long createMember(MemberSignDto signDto) {
        Member member = memberCreator.getMember(signDto);
        memberCreator.createMember(member);
        hobbyCreator.createHobbies(member, signDto.memberHobbyDto());
        tagCreator.createTags(member, signDto.memberTagDto());
        memberCreator.memberNickNameUpdate(member);
        return member.getMemberId();
    }

    /**
     * NOTE
     * 회원 PK값으로 DB에서 삭제 -> 회원탈퇴
     */
    @Transactional
    public String resignMember(String telNum) {
        Member member = memberReader.findByTelNum(telNum);
        chatRoomDeleter.deleteByMemberResign(member);
        memberDeleter.deleteMember(telNum);
        refreshDeleter.deleteRefreshToken(telNum);
        return telNum;
    }

    @Transactional
    public Long modifyAccount(String telNum, String password) {
        Member member = memberReader.findByTelNum(telNum);
        String encryptedPassword = memberUpdater.modifyPassword(password);
        memberUpdater.modifyMemberAccount(encryptedPassword, member);
        return member.getMemberId();
    }

    @Transactional(readOnly = true)
    public MemberProfileDto memberProfile(String telNum) { //멤버 프로필 조회
        Member member = memberReader.findByTelNum(telNum);
        return memberReader.getMemberProfileDto(member);
    }

    @Transactional(readOnly = true)
    public MemberProfileDto getMemberProfile(Long memberId) { //상대방 프로필 조회
        Member member = memberReader.findMember(memberId);
        return memberReader.getMemberProfileDto(member);
    }

    @Transactional
    public Long modifyProfile(String loginId, MemberInfoDto memberInfoDto) { // 사용자가 입력한 값이 들어있음
        Member member = memberReader.findByTelNum(loginId);
        memberUpdater.profileUpdate(memberInfoDto, member);
        String nickName = memberReader.memberNickName(member); // 닉네임 변경 전
        memberCreator.memberNickNameUpdate(member); // 닉네임 변경
        roomMemberProcessor.updateRoomName(member, nickName); //채팅방 방 이름 업데이트
        tagUpdater.memberTagUpdate(memberInfoDto.memberTagDto(), member);
        hobbyUpdater.memberHobbyUpdate(memberInfoDto.memberHobbyDto(), member);
        return member.getMemberId();
    }

    /**
     * NOTE
     * 상대방 번호 알아내는 메소드
     */
    @Transactional(readOnly = true)
    public MemberTelNumDto findTelNum(Long memberId, String telNum, Long chatRoomId) {
        Member me = memberReader.findByTelNum(telNum);
        Member opponent = memberReader.findMember(memberId);
        return communicationFacade.findTelNum(me, opponent, chatRoomId);
    }

    public void verifyAuthenticateNum(
        CheckAuthenticateNum checkAuthenticateNum,
        String authenticateNum
    ) {
        if (!memberVerifier.verifyNumber(checkAuthenticateNum.authenticateNum(), authenticateNum)) {
            throw new MemberException(NOT_CORRECT_AUTHENTICATION_NUMBER);
        }
    }

    public String verifyUniv(String telNum) {
        return memberVerifier.proofUniv(telNum);
    }

    public void memberLogout(String telNum) {
        Member member = memberReader.findByTelNum(telNum);
        memberDeleter.deleteClientToken(member);
    }

    /**
     * NOTE
     * 인증메일 전송
     */
    public String sendSms(TelNumRequest telNumRequest) {
        String num = getTempPassword(CHAR_SET_AUTHENTICATE_NUMBER);
        smsUtil.sendOne(telNumRequest, num);
        return num;
    }
}
