package com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "frontend_config")
public class FrontendConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "text_value")
    private String textValue;

    private String type;

    @Column(name = "numeric_value")
    private Long numericValue;

    @Column(name = "is_private")
    private Integer isPrivate;

}
