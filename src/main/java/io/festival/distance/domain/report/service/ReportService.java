package io.festival.distance.domain.report.service;

import io.festival.distance.domain.report.dto.ReportDto;
import io.festival.distance.domain.report.entity.Report;
import io.festival.distance.domain.report.repository.ReportRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberService memberService;
    private final ReportRepository declareRepository;
    @Transactional
    public void writeReport(ReportDto reportDto, Principal principal) {
        Member me = memberService.findByTelNum(principal.getName()); //나 => 신고하는 사람
        Member opponent = memberService.findMember(reportDto.opponentId()); // 상대방 => 신고받는 사람
        if(declareRepository.existsByMeAndOpponent(me,opponent))
            throw new DistanceException(ErrorCode.EXIST_DECLARE);

        memberService.increaseDeclare(opponent.getMemberId()); //신고하기 갯수 증가
        Report report = Report.builder()
                .reportContent(reportDto.reportContent())
                .me(me)
                .opponent(opponent)
                .build();
        declareRepository.save(report);
        if(opponent.getReportCount()==3)
            memberService.blockAccount(opponent.getMemberId());
    }
}
