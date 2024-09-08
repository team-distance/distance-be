package io.festival.distance.domain.statistics.service;

import static io.festival.distance.domain.statistics.service.serviceimpl.StatisticsTypeCheck.result;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.serviceimpl.MemberReader;
import io.festival.distance.domain.statistics.dto.response.CountResponse;
import io.festival.distance.domain.statistics.dto.response.StatisticsResponse;
import io.festival.distance.domain.statistics.entity.CouncilStatistics;
import io.festival.distance.domain.statistics.repository.StatisticsRepository;
import io.festival.distance.domain.statistics.service.serviceimpl.StatisticsTypeCheck;
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

        StatisticsTypeCheck result = result(date, type);

        Integer count = statisticsReader.findStatistics(councilId).getCount();

        Optional<CouncilStatistics> statistics =
            statisticsRepository.findByDateAndStudentCouncil(LocalDate.now(), studentCouncil)
                .or(() -> {
                    CouncilStatistics councilStatistics = CouncilStatistics.builder()
                        .count(1)
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
                result.startDate(),
                result.endDate()
            )
            .map(it -> StatisticsResponse.fromCount(result.startDate(), it))
            .orElse(StatisticsResponse.fromCount(result.startDate(), 0));
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
}
