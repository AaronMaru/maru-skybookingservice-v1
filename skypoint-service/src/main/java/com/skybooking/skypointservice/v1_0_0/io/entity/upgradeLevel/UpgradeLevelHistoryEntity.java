package com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "upgrade_level_history")
public class UpgradeLevelHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "upgrade_level_id")
    private Integer upgradeLevelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "from_value")
    private Integer fromValue;

    @Column(name = "to_value")
    private Integer toValue;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
