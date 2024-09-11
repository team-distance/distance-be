package io.festival.distance.domain.statistics.repository;

public interface BestStatisticsProjection {
    Long getCouncilId();
    String getTitle();
    Integer getCount();
}
