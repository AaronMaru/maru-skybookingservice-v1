package com.skybooking.skygatewayservice.io.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "frontend_translation")
public class FrontendTranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "locale_id")
    private Integer localeId;
    private String module;
    private String key;
    private String value;

}
