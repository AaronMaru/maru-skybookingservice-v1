package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.TicketingAction;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.implement.booking.BookingDataIP;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.ticketing.IssueTicketRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.ticketing.TicketRS;
import com.skybooking.skyflightservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/ticketing")
public class TicketingController {

    @Autowired
    private TicketingAction action;

    @Autowired
    private BookingDataIP bookingDataIP;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Autowired
    private BookingRP bookingRP;

    @PostMapping("/issued")
    public ResponseEntity create(@RequestBody IssueTicketRQ issueTicketRQ) {

        BookingEntity dataBooking = bookingRP.getBookingByBookingCode(issueTicketRQ.getBookingCode());

        issueTicketRQ.setItineraryNo(dataBooking.getItineraryRef());
        issueTicketRQ.setPassengerQualifier(dataBooking.getPq());

        /**
         * Request action for issue ticket
         */
        var ticket = action.issued(issueTicketRQ);
        var status = ticket.get("AirTicketRS").get("ApplicationResults").get("status").textValue();
        var user = activityLog.getUser(dataBooking.getStakeholderUserId());

        /**
         * Update ticket information
         */
        if (status.equals("Complete")) {
            bookingDataIP.updateTicket(ticket, dataBooking);
            activityLog.activities(ActivityLoggingBean.Action.INDEX_TICKETING, user, dataBooking);
            return new ResponseEntity<>(new TicketRS(status), HttpStatus.OK);
        }

        dataBooking.setStatus(TicketConstant.TICKET_FAIL);
        bookingRP.save(dataBooking);
        activityLog.activities(ActivityLoggingBean.Action.INDEX_TICKETING_FAIL, user, dataBooking);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
