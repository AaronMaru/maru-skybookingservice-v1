package com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_recent_search")
public class RecentSearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    @Column(name = "destination_code")
    private String destinationCode;

    private Date checkIn;
    private Date checkOut;
    private int room;
    private int adult;
    private int children;
    private int infant;

    @Column(name = "group_destination")
    private String groupDestination;

    private String country;

    @Column(name = "searched_count")
    private Integer searchedCount = 0;

}
