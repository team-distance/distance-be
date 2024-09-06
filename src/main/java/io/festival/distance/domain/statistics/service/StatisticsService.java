package io.festival.distance.domain.statistics.service;

import static io.festival.distance.utils.DateUtil.getEndOfMonth;
import static io.festival.distance.utils.DateUtil.getEndOfWeek;
import static io.festival.distance.utils.DateUtil.getStartOfMonth;
import static io.festival.distance.utils.DateUtil.getStartOfWeek;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.statistics.dto.response.CountResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponse;
import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsValidator;
import io.festival.distance.domain.studentcouncil.entity.StudentCouncil;
import io.festival.distance.domain.studentcouncil.service.serviceimpl.CouncilReader;
import io.festival.distance.infra.redis.statistics.StatisticsReader;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public StatisticsResponse checkStatistics(
        Long councilId,
        String telNum,
        String type,
        LocalDate date
    ) {
        StudentCouncil studentCouncil = councilReader.findStudentCouncil(councilId);
        Member member = memberReader.findTelNum(telNum);
        statisticsValidator.validateAuthority(studentCouncil, member);
        LocalDate startDate;
        LocalDate endDate;
        switch (type) {
            case "daily" -> {
                startDate = date;
                endDate = date;
            }
            case "weekly" -> {
                startDate = getStartOfWeek(date);
                endDate = getEndOfWeek(date);
            }
            case "monthly" -> {
                startDate = getStartOfMonth(date);
                endDate = getEndOfMonth(date);
            }
            default -> {
                return null;
            }
        }
        Integer count = statisticsReader.findStatistics(councilId).getCount();
        Optional<CouncilStatistics> statistics =
            statisticsRepository.findByDateAndStudentCouncil(LocalDate.now(), studentCouncil)
                .or(() -> {
                    CouncilStatistics councilStatistics = CouncilStatistics.builder()
                        .count(0)
                        .date(LocalDate.now())
                        .studentCouncil(studentCouncil)
                        .role(member.getAuthority())
                        .build();
                    statisticsRepository.save(councilStatistics);
                    return Optional.of(councilStatistics);
                });

        statistics.ifPresent(stat -> stat.updateCount(count));

        //최종 조회수 측정
        return statisticsRepository.findByStudentCouncilAndDateBetween(
                studentCouncil,
                startDate,
                endDate
            )
            .map(it -> StatisticsResponse.fromCount(startDate, it))
            .orElse(StatisticsResponse.fromCount(startDate, 0));
    }

    public StatisticsResponse checkTotalStatistics(String type, LocalDate date, String telNum) {
        Member member = memberReader.findTelNum(telNum);
        LocalDate startDate;
        LocalDate endDate;
        switch (type) {
            case "daily" -> {
                startDate = date;
                endDate = date;
            }
            case "weekly" -> {
                startDate = getStartOfWeek(date);
                endDate = getEndOfWeek(date);
            }
            case "monthly" -> {
                startDate = getStartOfMonth(date);
                endDate = getEndOfMonth(date);
            }
            default -> {
                return null;
            }
        }

        return statisticsRepository.findByDateBetween(startDate, endDate, member.getAuthority())
            .map(it -> StatisticsResponse.fromCount(startDate,it))
            .orElse(StatisticsResponse.fromCount(startDate,0));
    }

    public CountResponse calculateTotalCount(String telNum) {
        Member member = memberReader.findTelNum(telNum);
        Integer totalCount = statisticsReader.findTotalCount(member.getAuthority());
        return CountResponse.builder()
            .total(totalCount)
            .build();
    }
}
