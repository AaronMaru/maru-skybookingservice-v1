package com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "frontend_config")
@Data
public class FrontConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "numeric_value")
    private String numericValue;

    @Column(name = "text_value")
    private String textValue;

}
