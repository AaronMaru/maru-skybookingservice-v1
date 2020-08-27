package com.skybooking.skypointservice.v1_0_0.io.entity.config;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table
@Entity(name = "config_upgrade_levels")
public class ConfigUpgradeLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "from_value")
    private Integer fromValue;

    @Column(name = "to_value")
    private Integer toValue;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
