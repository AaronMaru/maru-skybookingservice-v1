package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.constant.TicketConstant;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.TicketingAction;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.action.SkyhistoryAction;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request.SendBookingPDFRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.TicketSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.ticketing.IssueTicketRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.ticketing.TicketRS;
import com.skybooking.skyflightservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.skyflightservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TicketIP implements TicketSV {

    @Autowired
    private TicketingAction action;

    @Autowired
    private BookingDataIP bookingDataIP;

    @Autowired
    private PushNotificationOptions pushNotification;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Autowired
    private BookingRP bookingRP;

    @Override
    public TicketRS issued(String bookingCode) {
        BookingEntity dataBooking = bookingRP.getBookingByBookingCode(bookingCode);

        IssueTicketRQ issueTicketRQ = new IssueTicketRQ();

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
            pushNotification.sendMessageToUsers("user_receive_flght_ticket", dataBooking.getId(), dataBooking.getStakeholderUserId().longValue());
            return new TicketRS(status);
        }

        dataBooking.setStatus(TicketConstant.TICKET_FAIL);
        bookingRP.save(dataBooking);
        activityLog.activities(ActivityLoggingBean.Action.INDEX_TICKETING_FAIL, user, dataBooking);
        pushNotification.sendMessageToUsers("user_booked_flight_failed", dataBooking.getId(), dataBooking.getStakeholderUserId().longValue());
        return new TicketRS("Failed");
    }

}
