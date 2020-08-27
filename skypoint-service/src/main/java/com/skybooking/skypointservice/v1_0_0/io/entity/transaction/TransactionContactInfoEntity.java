package com.skybooking.skypointservice.v1_0_0.io.entity.transaction;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "transaction_contact_info")
public class TransactionContactInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "phone_number")
    private String phoneNumber;
}
