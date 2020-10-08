package com.skybooking.skygatewayservice.io.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "frontend_locales")
public class FrontendLocaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "native_name")
    private String nativeName;
    private String name;
    private String locale;
    private int status;
}
