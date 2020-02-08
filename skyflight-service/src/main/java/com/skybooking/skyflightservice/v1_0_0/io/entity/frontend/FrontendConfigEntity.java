package com.skybooking.skyflightservice.v1_0_0.io.entity.frontend;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "frontend_config")
@Data
public class FrontendConfigEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "numeric_value")
    private BigDecimal numericValue;

    @Column(name = "text_value")
    private String textValue;

    @Column(name = "is_private")
    private int isPrivate;

    @Column(name = "type")
    private String type;
}
