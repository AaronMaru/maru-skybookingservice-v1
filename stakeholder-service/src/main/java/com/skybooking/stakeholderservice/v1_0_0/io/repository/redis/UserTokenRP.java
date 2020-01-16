package com.skybooking.stakeholderservice.v1_0_0.io.repository.redis;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTokenRP extends CrudRepository<UserTokenEntity, Long> {

//    UserTokenEntity findTopByUserIdOrderByIdAsc(Long id);

}
