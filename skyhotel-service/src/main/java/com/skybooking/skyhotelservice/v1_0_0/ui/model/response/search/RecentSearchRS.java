package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentSearchRS {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkOut;

    private int room;
    private int adult;
    private List<RecentSearchPax> children = new ArrayList<>();
    private RecentSearchDestination destination;

    public RecentSearchRS(RecentSearchEntity entity) {
        List<RecentSearchPax> recentSearchPaxes = new ArrayList<>();

        checkIn = entity.getCheckIn();
        checkOut = entity.getCheckOut();
        room = entity.getRoom();
        adult = entity.getAdult();

        destination = new RecentSearchDestination(
            entity.getDestinationCode(),
            entity.getGroupDestination(),
            "Cambodia"
        );

        if (entity.getChildren() > 0) {
            for (int i = 1; i <= entity.getChildren(); i++) {
                recentSearchPaxes.add(new RecentSearchPax(i));
            }
        }

        children = recentSearchPaxes;
    }
}

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class RecentSearchDestination {
    private String country;
    private String code;
    private String group;
    private String hotelCode;

    public RecentSearchDestination(String code, String group, String country) {
        this.code = code;
        this.group = group;
        this.country = country;
    }
}

@Data
@AllArgsConstructor
class RecentSearchPax {
    private Integer age;
}