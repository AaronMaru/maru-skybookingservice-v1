package com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "user_token", timeToLive = 60 * 60 * 24)
@Data
public class UserTokenEntity {

    @Id
    private Long userId;
    private String token;
    private String refreshToken;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

