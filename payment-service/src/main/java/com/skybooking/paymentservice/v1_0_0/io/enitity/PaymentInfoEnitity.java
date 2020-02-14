package com.skybooking.paymentservice.v1_0_0.io.enitity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dis_payment_method_details")
public class PaymentInfoEnitity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String code;
    private String method;
    private Double percentage;

    @Column(name = "payment_id")
    private Integer paymentId;
}
