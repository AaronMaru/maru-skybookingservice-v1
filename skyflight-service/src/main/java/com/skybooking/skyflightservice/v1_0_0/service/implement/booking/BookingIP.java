package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skyflightservice.config.WebClientConfiguration;
import com.skybooking.skyflightservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.BookingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingCancelDRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingDataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingMetadataTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking.PNRCreateRS;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.skybooking.skyflightservice.constant.BookingConstant.*;
import static com.skybooking.skyflightservice.constant.MessageConstant.NOT_FOUND;
import static com.skybooking.skyflightservice.constant.UserConstant.STAKEHOLDER_ID;


@Service
@Slf4j
public class BookingIP extends BookingDataIP implements BookingSV {

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfiguration.class.getName());

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private BookingAction bookingAction;

    @Autowired
    private BookingDataSV bookingDataSV;

    @Autowired
    private BookingRequestBody bookingRequestBody;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;


    @Override
    @SneakyThrows
    public PNRCreateRS create(BookingCreateRQ request, BookingMetadataTA metadataTA) {

        var bookingCreated = new PNRCreateRS();

        /** checking the shopping contain in cached. */
        if (!bookingUtility.isBookingCached(request.getRequestId())) {
            return bookingCreated;
        }

        /** action booking request */
        var mapper = new ObjectMapper();

        log.debug("BOOKING PNR: [{}]", request.getRequestId());

        var pnrRQ = bookingRequestBody.getBCreateDRQ(request);
        log.debug("BOOKING PNR REQUEST: {}", mapper.writeValueAsString(pnrRQ));

        var pnrRS = bookingAction.create(pnrRQ);
        log.debug("BOOKING PNR RESPONSE: {}", mapper.writeValueAsString(pnrRS));

        var status =
            pnrRS
                .get("CreatePassengerNameRecordRS")
                .get("ApplicationResults")
                .get("status")
                .textValue();

        if (status.equals("Complete")) {
            var itineraryID =
                pnrRS.get("CreatePassengerNameRecordRS").get("ItineraryRef").get("ID").textValue();
            var requestTA = new BookingRequestTA();

            /** action booking reservation */
            var segments =
                bookingAction
                    .reservation(itineraryID)
                    .get("PassengerReservation")
                    .get("Segments")
                    .get("Segment");

            if (segments.isArray()) {
                segments
                    .iterator()
                    .forEachRemaining(
                        it ->
                            requestTA
                                .getReservationsCode()
                                .add(it.get("Air").get("AirlineRefId").asText()));
            } else {
                if (segments.has("Air")) {
                    requestTA.getReservationsCode().add(segments.get("Air").get("AirlineRefId").asText());
                } else {
                    requestTA.getReservationsCode().add(segments.get("AirlineRefId").asText());
                }
            }

            requestTA.setRequest(request);
            requestTA.setItineraryCode(itineraryID);

            metadataTA = bookingUtility.getBookingMetadata(requestTA, metadataTA);

            /** store booking records */
            try {

                var booking = bookingDataSV.save(requestTA, metadataTA, pnrRS, request);

                bookingCreated.setBookingCode(booking.getBookingCode());

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return bookingCreated;
    }


    @Override
    public void cancel(String bookingCode) {

        var skyuserId = jwtUtils.getClaim(STAKEHOLDER_ID, Integer.class);

        Integer companyId = headerBean.getHeaders().getCompanyId();

        BookingEntity bookingEntity;
        if (companyId == null) {
            bookingEntity = bookingRP.getBookingToCancelUser(bookingCode, skyuserId);
        } else {
            bookingEntity = bookingRP.getBookingToCancelOwner(bookingCode, companyId);
        }

        if (bookingEntity == null) {
            throw new BadRequestException(NOT_FOUND, null);
        }

        this.cancelBookingSaberAndUpdateStatus(bookingEntity);
    }


    /**
     * cancel booking job
     */
    @Scheduled(fixedRateString = "${job.cancel-booking}")
    void cancelBookingJob() {

        logger.info("******************starting job*************************");
        List<BookingEntity> bookingEntityList = bookingRP.getBookingToCancelOwnerCron();

        bookingEntityList.forEach(
            bookingEntity -> {
                this.cancelBookingSaberAndUpdateStatus(bookingEntity);
                logger.info(
                    "cron booking cancel bookingId = " + bookingEntity.getId() + "at " + new Date());
            });
        logger.info("******************end job*************************");
    }

    private void cancelBookingSaberAndUpdateStatus(BookingEntity bookingEntity) {
        BookingCancelDRQ bookingCancelDRQ = new BookingCancelDRQ(bookingEntity.getItineraryRef());

        var cancelBooking = bookingAction.cancel(bookingCancelDRQ);

        if (cancelBooking.get(STATUS).textValue().equals(COMPLETE)) {
            bookingEntity.setStatus(PNR_CANCELLED);
        } else {
            bookingEntity.setStatus(PNR_CANCEL_FAIL);
        }
        bookingRP.save(bookingEntity);
    }
}
