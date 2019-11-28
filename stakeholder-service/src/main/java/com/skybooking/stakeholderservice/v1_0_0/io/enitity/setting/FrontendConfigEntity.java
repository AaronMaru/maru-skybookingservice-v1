package com.skybooking.stakeholderservice.v1_0_0.io.enitity.setting;

import javax.persistence.*;

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

    private Long numeric_value;
    private Integer is_private;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumeric_value() {
        return numeric_value;
    }

    public void setNumeric_value(Long numeric_value) {
        this.numeric_value = numeric_value;
    }

    public Integer getIs_private() {
        return is_private;
    }

    public void setIs_private(Integer is_private) {
        this.is_private = is_private;
    }
}
