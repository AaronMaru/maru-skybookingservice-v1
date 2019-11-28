package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookings",  uniqueConstraints={@UniqueConstraint(columnNames = "booking_code")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stakeholder_user_id", nullable = false)
    private StakeHolderUserEntity stakeHolderUser;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingAirticketEntity> bookingAirticket;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingOdEntity> bookingOd;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingPaymentTransactionsEntity> bookingPaymentTransactions;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingSpecialServicesEntity> bookingSpecialServices;

    @OneToMany(mappedBy = "bookingEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingTravelItinerariesEntity> bookingTravelItineraries;

    //stakeholder_company_id

    @Column(nullable = true)
    private String slug;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "booking_type")
    private String bookingType;

    @Column(name = "itinerary_ref")
    private String itineraryRef;

    @Column(name = "custName")
    private String cust_name;

    @Column(name = "cust_address")
    @Lob
    private String custAddress;

    @Column(name = "cust_city")
    private String custCity;

    @Column(name = "cust_zip")
    private String custZip;

    @Column(name = "cont_location_code")
    private String contLocationCode;

    @Column(name = "cont_phone", length = 50)
    private String contPhone;

    @Column(name = "cont_email", length = 50)
    private String contEmail;

    @Column(name = "cont_fullname", length = 70)
    private String contFullname;

    @Column(name = "dep_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date depDate;

    @Column(name = "currency_convert", length = 10)
    private String currencyConvert;

    @Column(name = "markup_percentage", nullable = true)
    private String markupPercentage;

    @Column(name = "trip_type", length = 50)
    private String tripType;

    @Column(name = "remark")
    @Lob
    private String remark;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "created_by", length = 45)
    private String createdBy;

    @Column(name = "updated_by", length = 45)
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "cont_phonecode")
    private String contPhonecode;

    private int pq = 1;

    @Column(name = "local_issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date localIssueDate;

    @Column(name = "currency_code", length = 5)
    private String currencyCode;

    private double vatPercentage = 0;

    @Column(name = "is_check")
    private int isCheck = 0;

    @Column(name = "itinerary_file")
    private String itineraryFile;

    @Column(name = "reciept_file")
    private String recieptFile;

    @Column(name = "itinerary_path")
    private String itineraryPath;

    @Column(name = "reciept_path")
    private String recieptPath;

    @Column(name = "currency_con_rate", columnDefinition="Decimal(10,5)")
    private BigDecimal currencyConRate;

    @Column(name = "total_amount", columnDefinition="Decimal(25,2)")
    private BigDecimal totalAmount;

    @Column(name = "markup_amount", columnDefinition="Decimal(25,2)")
    private BigDecimal markupAmount;

    @Column(name = "dis_pay_met_percentage", columnDefinition="Decimal(6,4) default '0.0000'")
    private BigDecimal disPayMetPercentage;

    @Column(name = "dis_pay_met_amount", columnDefinition="Decimal(25,2) default '0.00'")
    private BigDecimal disPayMetAmount;

    @Column(name = "markup_pay_percentage", columnDefinition="Decimal(8,4) default '0.0000'")
    private BigDecimal markupPayPercentage;

    @Column(name = "markup_pay_amount", columnDefinition="Decimal(25,2) default '0.00'")
    private BigDecimal markupPayAmount;

    @Column(name = "pay_met_fee_percentage", columnDefinition="Decimal(8,4) default '0.0000'")
    private BigDecimal payMetFeePercentage;

    @Column(name = "pay_met_fee_amount", columnDefinition="Decimal(25,2) default '0.00'")
    private BigDecimal payMetFeeAmount;

    @Column(name = "seen")
    @Lob
    private String seen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getItineraryRef() {
        return itineraryRef;
    }

    public void setItineraryRef(String itineraryRef) {
        this.itineraryRef = itineraryRef;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustZip() {
        return custZip;
    }

    public void setCustZip(String custZip) {
        this.custZip = custZip;
    }

    public String getContLocationCode() {
        return contLocationCode;
    }

    public void setContLocationCode(String contLocationCode) {
        this.contLocationCode = contLocationCode;
    }

    public String getContPhone() {
        return contPhone;
    }

    public void setContPhone(String contPhone) {
        this.contPhone = contPhone;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    public String getContFullname() {
        return contFullname;
    }

    public void setContFullname(String contFullname) {
        this.contFullname = contFullname;
    }

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public String getCurrencyConvert() {
        return currencyConvert;
    }

    public void setCurrencyConvert(String currencyConvert) {
        this.currencyConvert = currencyConvert;
    }

    public String getMarkupPercentage() {
        return markupPercentage;
    }

    public void setMarkupPercentage(String markupPercentage) {
        this.markupPercentage = markupPercentage;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContPhonecode() {
        return contPhonecode;
    }

    public void setContPhonecode(String contPhonecode) {
        this.contPhonecode = contPhonecode;
    }

    public int getPq() {
        return pq;
    }

    public void setPq(int pq) {
        this.pq = pq;
    }

    public Date getLocalIssueDate() {
        return localIssueDate;
    }

    public void setLocalIssueDate(Date localIssueDate) {
        this.localIssueDate = localIssueDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getItineraryFile() {
        return itineraryFile;
    }

    public void setItineraryFile(String itineraryFile) {
        this.itineraryFile = itineraryFile;
    }

    public String getRecieptFile() {
        return recieptFile;
    }

    public void setRecieptFile(String recieptFile) {
        this.recieptFile = recieptFile;
    }

    public String getItineraryPath() {
        return itineraryPath;
    }

    public void setItineraryPath(String itineraryPath) {
        this.itineraryPath = itineraryPath;
    }

    public String getRecieptPath() {
        return recieptPath;
    }

    public void setRecieptPath(String recieptPath) {
        this.recieptPath = recieptPath;
    }

    public BigDecimal getCurrencyConRate() {
        return currencyConRate;
    }

    public void setCurrencyConRate(BigDecimal currencyConRate) {
        this.currencyConRate = currencyConRate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getMarkupAmount() {
        return markupAmount;
    }

    public void setMarkupAmount(BigDecimal markupAmount) {
        this.markupAmount = markupAmount;
    }

    public BigDecimal getDisPayMetPercentage() {
        return disPayMetPercentage;
    }

    public void setDisPayMetPercentage(BigDecimal disPayMetPercentage) {
        this.disPayMetPercentage = disPayMetPercentage;
    }

    public BigDecimal getDisPayMetAmount() {
        return disPayMetAmount;
    }

    public void setDisPayMetAmount(BigDecimal disPayMetAmount) {
        this.disPayMetAmount = disPayMetAmount;
    }

    public BigDecimal getMarkupPayPercentage() {
        return markupPayPercentage;
    }

    public void setMarkupPayPercentage(BigDecimal markupPayPercentage) {
        this.markupPayPercentage = markupPayPercentage;
    }

    public BigDecimal getMarkupPayAmount() {
        return markupPayAmount;
    }

    public void setMarkupPayAmount(BigDecimal markupPayAmount) {
        this.markupPayAmount = markupPayAmount;
    }

    public BigDecimal getPayMetFeePercentage() {
        return payMetFeePercentage;
    }

    public void setPayMetFeePercentage(BigDecimal payMetFeePercentage) {
        this.payMetFeePercentage = payMetFeePercentage;
    }

    public BigDecimal getPayMetFeeAmount() {
        return payMetFeeAmount;
    }

    public void setPayMetFeeAmount(BigDecimal payMetFeeAmount) {
        this.payMetFeeAmount = payMetFeeAmount;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public List<BookingOdEntity> getBookingOd() {
        return bookingOd;
    }

    public void setBookingOd(List<BookingOdEntity> bookingOd) {
        this.bookingOd = bookingOd;
    }

    public List<BookingTravelItinerariesEntity> getBookingTravelItineraries() {
        return bookingTravelItineraries;
    }

    public void setBookingTravelItineraries(List<BookingTravelItinerariesEntity> bookingTravelItineraries) {
        this.bookingTravelItineraries = bookingTravelItineraries;
    }
}
