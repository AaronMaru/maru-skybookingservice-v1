package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    private String group;
    private String country;

    public RecentSearchRS(RecentSearchEntity entity) {
        List<RecentSearchPax> recentSearchPaxes = new ArrayList<>();

        checkIn = entity.getCheckIn();
        checkOut = entity.getCheckOut();
        room = entity.getRoom();
        adult = entity.getAdult();

        destination = new RecentSearchDestination(entity.getDestinationCode());

        if (entity.getChildren() > 0) {
            for (int i = 1; i <= entity.getChildren(); i++) {
                recentSearchPaxes.add(new RecentSearchPax(i));
            }
        }

        children = recentSearchPaxes;
    }
}

@Data
class RecentSearchDestination {
    private String destinationCode;
    private String hotelCode;

    public RecentSearchDestination(String destinationCode) {
        this.destinationCode = destinationCode;
    }
}

@Data
@AllArgsConstructor
class RecentSearchPax {
    private Integer age;
}