package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
public class BookingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stakeholder_user_id")
    private Integer stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Integer stakeholderCompanyId;

    @Column(name = "slug")
    private String slug;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name = "itinerary_ref")
    private String itineraryRef;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_address")
    private String custAddress;

    @Column(name = "cust_city")
    private String custCity;

    @Column(name = "cust_zip")
    private String custZip;

    @Column(name = "cont_location_code")
    private String contLocationCode;

    @Column(name = "cont_phone")
    private String contPhone;

    @Column(name = "cont_email")
    private String contEmail;

    @Column(name = "cont_fullname")
    private String contFullname;

    @Column(name = "dep_date")
    private Date depDate;

    @Column(name = "currency_convert")
    private String currencyConvert;

    @Column(name = "currency_con_rate")
    private BigDecimal currencyConRate;

    @Column(name = "markup_percentage")
    private String markupPercentage;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "markup_amount")
    private BigDecimal markupAmount;

    @Column(name = "trip_type")
    private String tripType;

    @Column(name = "remark")
    private String remark;

    @Column(name = "cont_phonecode")
    private String contPhonecode;

    @Column(name = "pq")
    private Integer pq;

    @Column(name = "local_issue_date")
    private Date localIssueDate;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "vat_percentage")
    private Double vatPercentage;

    @Column(name = "is_check")
    private Integer isCheck;

    @Column(name = "itinerary_file")
    private String itineraryFile;

    @Column(name = "reciept_file")
    private String recieptFile;

    @Column(name = "itinerary_path")
    private String itineraryPath;

    @Column(name = "reciept_path")
    private String recieptPath;

    @Column(name = "dis_pay_met_percentage")
    private BigDecimal disPayMetPercentage;

    @Column(name = "dis_pay_met_amount")
    private BigDecimal disPayMetAmount;

    @Column(name = "markup_pay_percentage")
    private BigDecimal markupPayPercentage;

    @Column(name = "markup_pay_amount")
    private BigDecimal markupPayAmount;

    @Column(name = "pay_met_fee_percentage")
    private BigDecimal payMetFeePercentage;

    @Column(name = "pay_met_fee_amount")
    private BigDecimal payMetFeeAmount;

    @Column(name = "seen")
    private String seen;

    @Column(name = "log_payment_res")
    private String logPaymentRes;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
