package io.festival.distance.infra.redis.authenticate;

import org.springframework.data.repository.CrudRepository;

public interface AuthenticateRedisRepository extends CrudRepository<AuthenticateNumber,String> {

}
