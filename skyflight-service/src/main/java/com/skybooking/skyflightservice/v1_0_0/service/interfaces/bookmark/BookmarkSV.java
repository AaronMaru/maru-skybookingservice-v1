package com.skybooking.skyflightservice.v1_0_0.service.interfaces.bookmark;

import com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark.FlightSavesEntity;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkAirline;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRS;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkSV {


    List<FlightSavesEntity> findAll(BookmarkCreateRQ bookmarkCreateRQ, UserAuthenticationMetaTA authenticationMetaTA);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete the favorite flight
     * -----------------------------------------------------------------------------------------------------------------
     *  @param itinerary
     * @param bookmarks @return
     */
    BookmarkCreateRS delete(String itinerary, List<FlightSavesEntity> bookmarks);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * bookmark favorite flight
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookmark
     * @param metadata
     * @return boolean
     */
    BookmarkCreateRS create(BookmarkCreateRQ bookmark, UserAuthenticationMetaTA metadata);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list favorite flight based on flight shopping request
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShoppingRQ
     * @param userAuthenticationMetadata
     * @return List
     */
    List<BookmarkAirline> get(FlightShoppingRQ flightShoppingRQ, UserAuthenticationMetaTA userAuthenticationMetadata);
}
