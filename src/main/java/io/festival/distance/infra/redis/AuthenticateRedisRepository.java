package io.festival.distance.infra.redis;

import org.springframework.data.repository.CrudRepository;

public interface AuthenticateRedisRepository extends CrudRepository<AuthenticateNumber,String> {

}
