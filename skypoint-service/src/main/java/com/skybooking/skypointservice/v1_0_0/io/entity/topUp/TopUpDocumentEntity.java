package com.skybooking.skypointservice.v1_0_0.io.entity.topUp;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "topup_documents")
public class TopUpDocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "file")
    private String file;

}
