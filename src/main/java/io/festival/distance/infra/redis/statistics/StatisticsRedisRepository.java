package io.festival.distance.infra.redis.statistics;

import org.springframework.data.repository.CrudRepository;

public interface StatisticsRedisRepository extends CrudRepository<Statistics,Long> {

}
