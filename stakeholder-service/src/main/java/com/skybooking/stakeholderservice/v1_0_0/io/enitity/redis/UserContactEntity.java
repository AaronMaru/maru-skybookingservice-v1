package com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "user_contact", timeToLive = 60 * 3)
@Data
public class UserContactEntity {
    private Long id;
    private String phoneCode;
    private String phoneNumber;
    private String email;
    private String password;
    private String playerId;
}
