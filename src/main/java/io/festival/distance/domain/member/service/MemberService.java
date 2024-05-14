package io.festival.distance.domain.member.service;

import static io.festival.distance.authuniversity.config.mail.SendMailService.CHAR_SET_AUTHENTICATE_NUMBER;
import static io.festival.distance.authuniversity.config.mail.SendMailService.getTempPassword;
import static io.festival.distance.exception.ErrorCode.NOT_CORRECT_AUTHENTICATION_NUMBER;

import io.festival.distance.auth.refresh.RefreshDeleter;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomDeleter;
import io.festival.distance.domain.conversation.chatroom.service.serviceimpl.ChatRoomReader;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberProcessor;
import io.festival.distance.domain.conversation.roommember.service.serviceimpl.RoomMemberReader;
import io.festival.distance.domain.member.dto.CheckAuthenticateNum;
import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.dto.TelNumRequest;
import io.festival.distance.domain.member.entity.Authority;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.entity.UnivCert;
import io.festival.distance.domain.member.service.serviceimpl.MemberCreator;
import io.festival.distance.domain.member.service.serviceimpl.MemberDeleter;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import io.festival.distance.domain.memberhobby.service.serviceimpl.HobbyCreator;
import io.festival.distance.domain.memberhobby.service.serviceimpl.HobbyReader;
import io.festival.distance.domain.memberhobby.service.serviceimpl.HobbyUpdater;
import io.festival.distance.domain.membertag.service.serviceimpl.TagCreator;
import io.festival.distance.domain.membertag.service.serviceimpl.TagReader;
import io.festival.distance.domain.membertag.service.serviceimpl.TagUpdater;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.infra.sms.SmsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    public static final String PREFIX = "#";
    private static final String INACTIVE = "INACTIVE";
    private final MemberCreator memberCreator;
    private final MemberDeleter memberDeleter;
    private final MemberUpdater memberUpdater;
    private final MemberReader memberReader;

    private final HobbyCreator hobbyCreator;
    private final HobbyUpdater hobbyUpdater;
    private final HobbyReader hobbyReader;

    private final TagCreator tagCreator;
    private final TagUpdater tagUpdater;
    private final TagReader tagReader;

    private final ChatRoomReader chatRoomReader;
    private final ChatRoomDeleter chatRoomDeleter;

    private final RoomMemberReader roomMemberReader;
    private final RefreshDeleter refreshDeleter;
    private final CommunicationFacade communicationFacade;
    private final SmsUtil smsUtil;
    private final RoomMemberProcessor roomMemberProcessor;

    /** NOTE
     * 회원가입
     */
    @Transactional
    public Long createMember(MemberSignDto signDto) {
        Member member = getMember(signDto);
        hobbyCreator.create(member, signDto.memberHobbyDto());
        tagCreator.create(member, signDto.memberTagDto());
        memberCreator.memberNickNameUpdate(member);
        return member.getMemberId();
    }

    /**
     * NOTE
     * 회원 PK값으로 DB에서 삭제 -> 회원탈퇴
     */
    @Transactional
    public String withDrawal(String telNum) {
        Member member = memberReader.findByTelNum(telNum);
        withDrawalChatRoom(member);
        memberDeleter.deleteMember(telNum);
        refreshDeleter.deleteRefreshToken(telNum);
        return telNum;
    }

    private void withDrawalChatRoom(Member member) {
        List<Long> chatRoomIdList = roomMemberReader.roomMemberList(member);
        for (Long chatRoomId : chatRoomIdList) {
            ChatRoom chatRoom = chatRoomReader.findChatRoom(chatRoomId);
            if (chatRoom.getRoomStatus().equals(INACTIVE)) {
                chatRoomDeleter.delete(chatRoomId);
                continue;
            }
            chatRoom.roomInActive();
        }
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
        return getMemberProfileDto(member);
    }

    @Transactional(readOnly = true)
    public MemberProfileDto getMemberProfile(Long memberId) { //상대방 프로필 조회
        Member member = memberReader.findMember(memberId);
        return getMemberProfileDto(member);
    }

    private MemberProfileDto getMemberProfileDto(Member member) {
        List<MemberHobbyDto> hobbyDtoList = hobbyReader.getHobbyList(member);
        List<MemberTagDto> tagDtoList = tagReader.getTagList(member);
        return MemberProfileDto.builder()
            .memberCharacter(member.getMemberCharacter())
            .mbti(member.getMbti())
            .memberTagDto(tagDtoList)
            .department(member.getDepartment())
            .memberHobbyDto(hobbyDtoList)
            .build();
    }

    @Transactional
    public Long modifyProfile(String loginId, MemberInfoDto memberInfoDto) { // 사용자가 입력한 값이 들어있음
        Member member = memberReader.findByTelNum(loginId);
        memberUpdater.profileUpdate(memberInfoDto, member);
        String nickName = member.getNickName(); // 닉네임 변경 전
        memberCreator.memberNickNameUpdate(member); // 닉네임 변경
        roomMemberProcessor.updateRoomName(member, nickName); //채팅방 방 이름 업데이트
        tagUpdater.memberTagUpdate(memberInfoDto.memberTagDto(), member);
        hobbyUpdater.memberHobbyUpdate(memberInfoDto.memberHobbyDto(),member);
        return member.getMemberId();
    }



    @Transactional(readOnly = true)
    public MemberTelNumDto findTelNum(Long memberId, String telNum, Long chatRoomId) {
        Member me = memberReader.findByTelNum(telNum);
        Member opponent = memberReader.findMember(memberId);
       return communicationFacade.findTelNum(me,opponent,chatRoomId);
    }

    /**
     * NOTE
     * 인증메일 전송
     *
     * @param telNumRequest 전화번호
     */
    public String sendSms(TelNumRequest telNumRequest) {
        String num = getTempPassword(CHAR_SET_AUTHENTICATE_NUMBER);
        smsUtil.sendOne(telNumRequest, num);
        return num;
    }

    public void verifyAuthenticateNum(
        CheckAuthenticateNum checkAuthenticateNum,
        String authenticateNum
    ) {
        if (!authenticateNum.equals(checkAuthenticateNum.authenticateNum())) {
            throw new DistanceException(NOT_CORRECT_AUTHENTICATION_NUMBER);
        }
    }

    public String verifyUniv(String name) {
        return memberReader.findByTelNum(name).getAuthUniv().getType();
    }

    @Transactional
    public void memberLogout(String telNum) {
        Member member = memberReader.findByTelNum(telNum);
        member.clearInfo();
    }

    private Member getMember(MemberSignDto signDto) {
        Member member = Member.builder()
            .password(memberUpdater.modifyPassword(signDto.password()))
            .gender(signDto.gender())
            .telNum(signDto.telNum())
            .authority(Authority.ROLE_USER)
            .mbti(signDto.mbti())
            .department(signDto.department())
            .college(signDto.college())
            .school(signDto.school())
            .memberCharacter(signDto.memberCharacter())
            .nickName(signDto.department())
            .reportCount(0)
            .authUniv(UnivCert.FAILED_1)
            .activated(true)
            .build();
       return memberCreator.createMember(member);
    }
}
