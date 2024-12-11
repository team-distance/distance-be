package io.festival.distance.domain.member.service.serviceimpl;


import static io.festival.distance.global.exception.ErrorCode.NOT_EXIST_MEMBER;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberProfileDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.memberhobby.service.HobbyReader;
import io.festival.distance.domain.membertag.service.TagReader;
import io.festival.distance.global.exception.DistanceException;
import io.festival.distance.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;
    private final HobbyReader hobbyReader;
    private final TagReader tagReader;

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    public Member findTelNum(String telNum) {
        return memberRepository.findByTelNum(telNum)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    public Member findNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
            .orElseThrow(() -> new DistanceException(NOT_EXIST_MEMBER));
    }

    public Member findNickNameNullAble(String nickName) {
        return memberRepository.findByNickName(nickName)
            .orElseThrow(()-> new DistanceException(NOT_EXIST_MEMBER));
    }

    public String memberNickName(Member member) {
        return member.getNickName();
    }

    public MemberProfileDto getMemberProfileDto(Member member) {
        List<MemberHobbyDto> hobbyDtoList = hobbyReader.showHobbiesDto(member);
        List<MemberTagDto> tagDtoList = tagReader.showTagsDto(member);
        return MemberProfileDto.builder()
            .memberCharacter(member.getMemberCharacter())
            .nickName(member.getNickName())
            .mbti(member.getMbti())
            .memberTagDto(tagDtoList)
            .memberHobbyDto(hobbyDtoList)
            .department(member.getDepartment())
            .build();
    }

    @Transactional(readOnly = true)
    public List<Member> findMemberList() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Member> findMemberListByFilter(){
        LocalDateTime oneWeekAgo = ZonedDateTime
            .now(ZoneId.of("Asia/Seoul"))
            .minusWeeks(1)
            .toLocalDateTime();
        return memberRepository.findByModifyDtAfter(oneWeekAgo);
    }

    @Transactional(readOnly = true)
    public List<Member> findMemberListBySchool(String school, String gender) {
        return memberRepository.findAllBySchoolAndGender(school,gender);
    }

    @Transactional(readOnly = true)
    public List<Member> findRandomMember(
        String character,
        String school,
        String gender
    ) {
        return memberRepository.findAllBySchoolAndMemberCharacter(school, character, gender);
    }
}
