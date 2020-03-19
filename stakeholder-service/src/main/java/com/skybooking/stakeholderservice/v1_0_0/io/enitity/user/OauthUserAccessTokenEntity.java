package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "oauth_user_access_tokens")
public class OauthUserAccessTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jwt_id")
    private String jwtId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "scopes")
    private String scopes;

    @Column(name = "status")
    private Integer status;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
