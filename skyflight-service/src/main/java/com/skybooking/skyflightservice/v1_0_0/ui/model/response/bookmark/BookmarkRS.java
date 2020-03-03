package com.skybooking.skyflightservice.v1_0_0.ui.model.response.bookmark;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import org.springframework.http.HttpStatus;

public class BookmarkRS extends BaseRS {

    public BookmarkRS(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public BookmarkRS(HttpStatus httpStatus, Object data) {
        super(httpStatus, data);
    }

    public BookmarkRS(HttpStatus httpStatus, String message, Object data) {
        super(httpStatus, message, data);
    }

    public BookmarkRS(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
