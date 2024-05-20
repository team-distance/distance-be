package io.festival.distance.domain.report.service;


import static io.festival.distance.global.exception.ErrorCode.EXIST_DECLARE;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.member.service.serviceimpl.MemberUpdater;
import io.festival.distance.domain.report.dto.ReportDto;
import io.festival.distance.domain.report.entity.Report;
import io.festival.distance.domain.report.service.serviceimpl.ReportCreator;
import io.festival.distance.domain.report.service.serviceimpl.ReportVerifier;
import io.festival.distance.global.exception.DistanceException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberReader memberReader;
    private final MemberUpdater memberUpdater;
    private final ReportCreator reportCreator;
    private final ReportVerifier reportVerifier;

    /** NOTE
     * 신고하기 메소드
     */
    @Transactional
    public void writeReport(ReportDto reportDto, Principal principal) {
        Member me = memberReader.findTelNum(principal.getName()); //나 => 신고하는 사람
        Member opponent = memberReader.findMember(reportDto.opponentId()); // 상대방 => 신고받는 사람

        if(reportVerifier.verifyReport(me,opponent)) {
            throw new DistanceException(EXIST_DECLARE);
        }

        memberUpdater.updateReport(opponent); //신고하기 갯수 증가
        Report report = reportCreator.getReport(me, opponent, reportDto.reportContent());
        reportCreator.create(report);

        if(opponent.getReportCount()==3)
            memberUpdater.updateStatus(opponent);
    }
}
