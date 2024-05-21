package io.festival.distance.domain.member.controller;

import io.festival.distance.domain.member.dto.AccountRequestDto;
import io.festival.distance.domain.member.dto.ChangePasswordDto;
import io.festival.distance.domain.member.dto.CheckAuthenticateNum;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.dto.MemberSignDto;
import io.festival.distance.domain.member.dto.MemberTelNumDto;
import io.festival.distance.domain.member.dto.TelNumRequest;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.member.usecase.MemberUseCase;
import io.festival.distance.domain.member.usecase.SendSmsUseCase;
import io.festival.distance.domain.member.validsignup.ValidPassword;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;
    private final MemberUseCase memberUseCase;
    private final SendSmsUseCase sendSmsUseCase;
    private final ValidPassword validPassword;
    private final MemberReader memberReader;
    private String authenticateNum;


    /** NOTE
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberSignDto signDto) {
        return ResponseEntity.ok(memberUseCase.execute(signDto));
    }

    /**
     * NOTE
     * 회원 탈퇴 API
     */
    @DeleteMapping
    public ResponseEntity<String> delete(Principal principal) {
        return ResponseEntity.ok(memberService.resignMember(principal.getName()));
    }

    /**
     * NOTE
     * 멤버 계정 수정
     */
    @PatchMapping("/account/update")
    public ResponseEntity<Long> updateAccount(Principal principal,
        @RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(
            memberService.modifyAccount(principal.getName(), accountRequestDto.password()));
    }

    /**
     * NOTE
     * 멤버 프로필 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<MemberProfileDto> showProfile(Principal principal) {
        return ResponseEntity.ok(memberService.memberProfile(principal.getName()));
    }

    /** NOTE
     * 채팅방에서 상대방 프로필 조회
     * @param memberId 상대방 Id
     */
    @GetMapping("/profile/{memberId}")
    public ResponseEntity<MemberProfileDto> memberProfileInfo(
        @PathVariable Long memberId,
        Principal principal
    ) {
        return ResponseEntity.ok(memberService.getMemberProfile(memberId));
    }

    /** NOTE
     * 멤버 프로필 수정
     */
    @PatchMapping("/profile/update")
    public ResponseEntity<Long> updateProfile(Principal principal,
        @RequestBody MemberInfoDto memberInfoDto) {
        return ResponseEntity.ok(memberService.modifyProfile(principal.getName(), memberInfoDto));
    }

    /** NOTE
     * 멤버 ID값 반환
     */
    @GetMapping("/id")
    public ResponseEntity<Long> sendMemberId(Principal principal) {
        return ResponseEntity.ok(memberReader.findTelNum(principal.getName()).getMemberId());
    }

    /** NOTE
     * 사용자 전화번호 조회 (10번 티키타카 한 경우)
     */
    @GetMapping("/tel-num")
    public ResponseEntity<MemberTelNumDto> getTelNum(
        @RequestParam(name = "memberId") Long memberId,
        @RequestParam(name = "chatRoomId") Long chatRoomId,
        Principal principal) {
        return ResponseEntity.ok(
            memberService.findTelNum(memberId, principal.getName(), chatRoomId));
    }

    /** NOTE
     * 메시지 전송
     */
    @PostMapping("/send/sms")
    public ResponseEntity<Void> sendSms(@RequestBody TelNumRequest telNumRequest) {
        authenticateNum = sendSmsUseCase.execute(telNumRequest);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 메시지 인증번호 인증
     * @param checkAuthenticateNum 사용자가 입력한 인증번호
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticateNum(
        @RequestBody CheckAuthenticateNum checkAuthenticateNum) {
        memberService.verifyAuthenticateNum(checkAuthenticateNum, authenticateNum);
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 멤버가 대학인증을 했는지 안했는지 여부
     * @param principal 현재 로그인한 객체
     * @return 인증되어있다면 true, 안되어있으면 false
     */
    @GetMapping("/check/university")
    public ResponseEntity<String> checkIdentity(Principal principal) {
        return ResponseEntity.ok(memberService.verifyUniv(principal.getName()));
    }

    /** NOTE
     * logout
     * @param principal 현재 로그인한 객체
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal) {
        memberService.memberLogout(principal.getName());
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 비밀번호 확인
     * @param accountRequestDto 사용자가 입력한 비밀번호
     * @param principal         현재 로그인한 객체
     */
    @PostMapping("/check/password")
    public ResponseEntity<Void> checkPassword(@RequestBody AccountRequestDto accountRequestDto,
        Principal principal) {
        validPassword.duplicateCheckPassword(principal, accountRequestDto.password());
        return ResponseEntity.ok().build();
    }

    /** NOTE
     * 비밀번로 변경 API
     */
    @PostMapping("/change/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        memberService.modifyAccount(changePasswordDto.telNum(), changePasswordDto.password());
        return ResponseEntity.ok().build();
    }
}
