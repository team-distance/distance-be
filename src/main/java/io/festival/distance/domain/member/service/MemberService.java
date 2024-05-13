package io.festival.distance.domain.member.service;

import static io.festival.distance.authuniversity.config.mail.SendMailService.CHAR_SET_AUTHENTICATE_NUMBER;
import static io.festival.distance.authuniversity.config.mail.SendMailService.getTempPassword;
import static io.festival.distance.exception.ErrorCode.NOT_CORRECT_AUTHENTICATION_NUMBER;
import static io.festival.distance.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.service.ChatRoomService;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.conversation.roommember.service.RoomMemberProcessor;
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
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.infra.sms.SmsUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCreate memberCreate;
    private final MemberDelete memberDelete;
    private final MemberAccount memberAccount;
    private final MemberProfile memberProfile;
    private final CommunicationFacade communicationFacade;
    private final ChatRoomService chatRoomService;
    private final RoomMemberRepository roomMemberRepository;
    private final SmsUtil smsUtil;
    private final RoomMemberProcessor roomMemberProcessor;
    public static final String PREFIX = "#";
    private static final String INACTIVE = "INACTIVE";

    /**
     * NOTE
     * 회원가입
     */
    @Transactional
    public Long createMember(MemberSignDto signDto) {
        Member member = getMember(signDto);
        memberCreate.memberHobbyUpdate(member, signDto);
        memberCreate.memberTagUpdate(member, signDto);
        memberCreate.memberNickNameUpdate(member);
        return member.getMemberId();
    }

    /**
     * NOTE
     * 회원 PK값으로 DB에서 삭제 -> 회원탈퇴
     */
    @Transactional
    public String withDrawal(String telNum) {
        Member member = findByTelNum(telNum);
        withDrawalChatRoom(member);
        memberDelete.deleteMember(telNum);
        memberDelete.deleteRefreshToken(telNum);
        return telNum;
    }

    private void withDrawalChatRoom(Member member) {
        List<Long> chatRoomIdList = roomMemberRepository.findAllByMember(member)
            .stream()
            .map(chatRoomId -> chatRoomId.getChatRoom().getChatRoomId())
            .toList();
        for (Long chatRoomId : chatRoomIdList) {
            ChatRoom chatRoom = chatRoomService.findRoom(chatRoomId);
            if (chatRoom.getRoomStatus().equals(INACTIVE)) {
                chatRoomService.delete(chatRoomId);
                continue;
            }
            chatRoom.roomInActive();
        }
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    public Member findByTelNum(String telNum) {
        return memberRepository.findByTelNum(telNum)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    @Transactional
    public Long modifyAccount(String telNum, String password) {
        Member member = findByTelNum(telNum);
        String encryptedPassword = memberAccount.modifyPassword(password);
        memberAccount.modifyMemberAccount(encryptedPassword, member);
        return member.getMemberId();
    }

    @Transactional(readOnly = true)
    public MemberProfileDto memberProfile(String telNum) { //멤버 프로필 조회
        Member member = findByTelNum(telNum);
        return getMemberProfileDto(member);
    }

    @Transactional(readOnly = true)
    public MemberProfileDto getMemberProfile(Long memberId) { //상대방 프로필 조회
        Member member = findMember(memberId);
        return getMemberProfileDto(member);
    }

    private MemberProfileDto getMemberProfileDto(Member member) {
        List<MemberHobbyDto> hobbyDtoList = memberProfile.getHobbyList(member);
        List<MemberTagDto> tagDtoList = memberProfile.getTagList(member);
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
        Member member = findByTelNum(loginId);
        memberProfile.profileUpdate(memberInfoDto, member);
        String nickName = member.getNickName(); // 닉네임 변경 전
        memberCreate.memberNickNameUpdate(member); // 닉네임 변경
        roomMemberProcessor.updateRoomName(member, nickName); //채팅방 방 이름 업데이트
        memberProfile.memberTagUpdate(memberInfoDto.memberTagDto(), member);
        memberProfile.memberHobbyUpdate(memberInfoDto.memberHobbyDto(),member);
        return member.getMemberId();
    }



    @Transactional(readOnly = true)
    public MemberTelNumDto findTelNum(Long memberId, String telNum, Long chatRoomId) {
        Member me = findByTelNum(telNum);
        Member opponent = findMember(memberId);
       return communicationFacade.findTelNum(me,opponent,chatRoomId);
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
        return findByTelNum(name).getAuthUniv().getType();
    }

    @Transactional
    public void memberLogout(String telNum) {
        Member member = findByTelNum(telNum);
        member.clearInfo();
    }

    private Member getMember(MemberSignDto signDto) {
        Member member = Member.builder()
            .password(memberAccount.modifyPassword(signDto.password()))
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
        return memberRepository.save(member);
    }
}
