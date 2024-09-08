package io.festival.distance.domain.statistics.service;

import static io.festival.distance.domain.statistics.dto.response.StatisticsResultResponse.toResult;
import static io.festival.distance.domain.statistics.service.serviceimpl.StatisticsTypeCheck.result;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.statistics.dto.response.BestStatisticsResponse;
import io.festival.distance.domain.statistics.dto.response.CountResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponses;
import io.festival.distance.domain.statistics.dto.response.StatisticsResultResponse;
import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsTypeCheck;
import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsUpdater;
import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsValidator;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.infra.redis.statistics.StatisticsReader;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final MemberReader memberReader;
    private final CouncilReader councilReader;
    private final StatisticsValidator statisticsValidator;
    private final StatisticsReader statisticsReader;
    private final StatisticsUpdater statisticsUpdater;

    @Transactional
    public StatisticsResultResponse checkStatistics(
        Long councilId,
        String telNum,
        String type,
        LocalDate date,
        Integer countWeek
    ) {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(councilId);
        Member member = memberReader.findTelNum(telNum);
        statisticsValidator.validateAuthority(studentCouncil, member);
        StatisticsResponses statisticsResponses = StatisticsResponses.builder()
            .responsesList(new ArrayList<>())
            .build();
        StatisticsTypeCheck result = StatisticsTypeCheck.resultByCount(date, type, countWeek);

        Integer count = statisticsReader.findStatistics(councilId).getCount();

        // NOTE -> 오늘 자 조회수 업데이트 로직
        statisticsUpdater.update(councilId,count);

        // NOTE -> 최종 조회수 측정
        if ("daily".equals(type)) {
            // 날짜 범위 내의 각 날짜를 처리 (countWeek 만큼만 날짜 생성)
            result.endDate().minusDays(countWeek - 1).datesUntil(result.endDate().plusDays(1))  // 종료일 포함
                .forEach(currentDate -> {
                    int statisticsCount = statisticsRepository.findByStudentCouncilAndDateBetween(
                        studentCouncil, currentDate, currentDate
                    ).orElse(0);  // 각 날짜별 조회수
                    statisticsResponses.addResponse(
                        StatisticsResponse.toStatisticsResponse(currentDate, statisticsCount)
                    );
                });
        } else {
            // 주간 또는 월간 처리: 범위 내 전체 데이터를 한 번에 처리
            for (LocalDate currentStart = result.startDate();
                !currentStart.isAfter(result.endDate());
                currentStart = "weekly".equals(type) ? currentStart.plusWeeks(1)
                    : currentStart.plusMonths(1)) {

                LocalDate currentEnd = "weekly".equals(type)
                    ? currentStart.plusDays(6)  // 주간은 7일 범위
                    : currentStart.with(TemporalAdjusters.lastDayOfMonth());  // 월간은 그 달의 마지막 날까지

                int statisticsCount = statisticsRepository.findByStudentCouncilAndDateBetween(
                    studentCouncil, currentStart, currentEnd
                ).orElse(0);  // 주간/월간 조회수
                statisticsResponses.addResponse(
                    StatisticsResponse.toStatisticsResponse(currentStart, statisticsCount)
                );

            }

        }
        return toResult(studentCouncil.getTitle(),studentCouncil.getCreateDt(),statisticsResponses);
    }


    public StatisticsResponse checkTotalStatistics(String type, LocalDate date, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        StatisticsTypeCheck result = result(date, type);

        return statisticsRepository.findByDateBetween(
                result.startDate(),
                result.endDate(),
                member.getAuthority()
            )
            .map(it -> StatisticsResponse.fromCount(result.startDate(), it))
            .orElse(StatisticsResponse.fromCount(result.startDate(), 0));
    }

    public CountResponse calculateTotalCount(String telNum) {
        Member member = memberReader.findTelNum(telNum);
        Integer totalCount = statisticsReader.findTotalCount(member.getAuthority());
        return CountResponse.builder()
            .total(totalCount)
            .build();
    }

    public CountResponse calculateTotalCountByCouncil(Long councilId) {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(councilId);
        Integer totalCountByCouncil = statisticsReader.findTotalCountByCouncil(studentCouncil);
        return CountResponse.builder()
            .total(totalCountByCouncil)
            .build();
    }

    public List<BestStatisticsResponse> findBestCouncil() {
        return statisticsRepository.getBestStatisticsCouncil(PageRequest.of(0,3))
            .stream()
            .map(BestStatisticsResponse::toBestStatisticsResponse)
            .collect(Collectors.toList());
    }
}
