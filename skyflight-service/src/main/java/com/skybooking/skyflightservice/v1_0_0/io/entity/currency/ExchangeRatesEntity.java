package com.skybooking.skyflightservice.v1_0_0.io.entity.currency;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rates")
@Data
public class ExchangeRatesEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private java.util.Date date;

    @Column(name = "rates")
    private String rates;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;

}
