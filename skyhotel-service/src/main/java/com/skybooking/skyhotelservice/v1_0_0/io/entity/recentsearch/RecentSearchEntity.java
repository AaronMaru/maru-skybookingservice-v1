package com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_recent_search")
public class RecentSearchEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stakeholder_user_id")
    private Integer stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Integer stakeholderCompanyId;

    @Column(name = "destination_code")
    private String destinationCode;

    private Date checkIn;
    private Date checkOut;
    private int room;
    private int adult;
    private int children;
    private int infant;

    @Column(name = "searched_count")
    private Integer searchedCount;

}
