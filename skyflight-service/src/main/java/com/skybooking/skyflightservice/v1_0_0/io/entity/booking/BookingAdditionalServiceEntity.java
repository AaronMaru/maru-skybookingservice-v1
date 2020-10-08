package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking_additional_services")
@Data
public class BookingAdditionalServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "service_happened_at")
    private Date serviceHappenedAt;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_fee")
    private BigDecimal supplierFee;

    @Column(name = "supplier_description")
    private String supplierDescription;

    @Column(name = "customer_fee")
    private BigDecimal customerFee;

    @Column(name = "customer_description")
    private String customerDescription;

    @Column(name = "bank_received_date")
    private Date bankReceivedDate;

    @Column(name = "bank_no")
    private String bankNo;

    @Column(name = "bank_fee")
    private BigDecimal bankFee;

    @Column(name = "bank_description")
    private String bankDescription;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

}
