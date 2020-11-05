package com.skybooking.stakeholderservice.v1_0_0.io.repository.redis;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactCachedRP extends CrudRepository<UserContactEntity, Long> {
}
