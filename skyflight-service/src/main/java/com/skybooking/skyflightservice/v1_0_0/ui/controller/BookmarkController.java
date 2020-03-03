package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.constant.MessageConstant;
import com.skybooking.skyflightservice.constant.UserConstant;
import com.skybooking.skyflightservice.exception.httpstatus.BadRequestRS;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.MetadataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.bookmark.BookmarkSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.header.SkyownerHeaderSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.bookmark.BookmarkRS;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/bookmark")
public class BookmarkController {

    @Autowired
    private SkyownerHeaderSV skyownerHeaderSV;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private Localization locale;

    @Autowired
    @Qualifier(value = "MetadataIP")
    private MetadataSV metadataSV;

    @Autowired
    private BookmarkSV bookmarkSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * bookmark the favorite flight
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param headers
     * @param request
     * @param itinerary
     * @return ResponseEntity
     */
    @GetMapping("/flight")
    public ResponseEntity bookmarkFlight(@RequestHeader HttpHeaders headers, @RequestParam(name = "request") String request, @RequestParam(name = "itinerary") String itinerary) {


        var authenticated = metadataSV.getUserAuthenticationMetadata();

        if (authenticated.getUserType().equalsIgnoreCase(UserConstant.SKYOWNER)) {

            /**
             * Check headers
             */
            if (!skyownerHeaderSV.check(headers)) {
                return new ResponseEntity(new BadRequestRS(locale.multiLanguageRes(MessageConstant.BAD_REQUEST), headerBean.getSkyownerHeaderMissing()), HttpStatus.BAD_REQUEST);
            }
        }

        BookmarkCreateRS created = bookmarkSV.create(new BookmarkCreateRQ(request, itinerary), authenticated);

        if (created == null) {
            return new ResponseEntity(new BookmarkRS(HttpStatus.BAD_REQUEST, "fail"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new BookmarkRS(HttpStatus.OK, "success", created), HttpStatus.OK);

    }

}
