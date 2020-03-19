package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail.*;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingDetailSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking.FilterRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.*;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingDetailIP implements BookingDetailSV {

    @Autowired
    private BookingDetailNQ bookingDetailNQ;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return a booking
     * @Param id
     */
    public BookingDetailRS getBookingDetail(Long bookingId) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        Long companyId = filterRQ.getCompanyHeaderId();
        String userType = headerBean.getCompanyId(companyId);

        BookingKeyConstant constant = new BookingKeyConstant();

        BookingDetailTO bookingDetailTO = bookingDetailNQ.bookingDetail(
                userType,
                bookingId,
                filterRQ.getSkyuserId(),
                companyId,
                filterRQ.getRole(),
                headerBean.getLocalizationId(),
                constant.COMPLETED,
                constant.UPCOMING,
                constant.CANCELLED,
                constant.FAILED,
                constant.ONEWAY,
                constant.ROUND,
                constant.MULTICITY,
                environment.getProperty("spring.awsImageUrl.profile.url_small")
        );

        if (bookingDetailTO == null)
            throw new NotFoundException("This url not found", null);

        /**
         * Get Baggage Information
         */
        List<BaggageInfoRS> baggageInfoRS = getBaggageInfoRS(bookingId);

        /**
         * Get Ticket Information
         */
        List<TicketInfoRS> ticketInfoRS = getTicketInfoRS(bookingId);


        /**
         * Object booking information
         */
        BookingInfoRS bookingInfoRS = new BookingInfoRS();
        BeanUtils.copyProperties(bookingDetailTO, bookingInfoRS);

        /**
         * Object contact information
         */
        ContactInfoRS contactInfoRS = new ContactInfoRS();
        BeanUtils.copyProperties(bookingDetailTO, contactInfoRS);

        /**
         * Object Payment information
         */

        PaymentInfoRS paymentInfoRS = new PaymentInfoRS();
        BeanUtils.copyProperties(bookingDetailTO, paymentInfoRS);

        /**
         * Object booking price
         */
        PriceInfoRS priceInfoRS = new PriceInfoRS();
        BeanUtils.copyProperties(bookingDetailTO, priceInfoRS);

        /**
         * Get Base Price Break Down Price
         */

        List<PriceBreakdownRS> priceBreakdownRSS = getPriceBreakdownRS(bookingId);
        priceInfoRS.setPriceBreakdown(priceBreakdownRSS);

        /**
         * Get Itinerary Information
         */
        List<ItineraryODInfoRS> itineraryODInfoRS = getItineraryODInfoRS(bookingId);


        /**
         * Response booking detail
         */
        BookingDetailRS bookingDetailRS = new BookingDetailRS();

        bookingDetailRS.setBookingInfo(bookingInfoRS);
        bookingDetailRS.setContactInfo(contactInfoRS);
        bookingDetailRS.setPaymentInfo(paymentInfoRS);
        bookingDetailRS.setBaggageInfo(baggageInfoRS);
        bookingDetailRS.setItineraryInfo(itineraryODInfoRS);
        bookingDetailRS.setTicketInfo(ticketInfoRS);
        bookingDetailRS.setPriceInfo(priceInfoRS);

        return bookingDetailRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Itinerary Information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return
     */
    public List<ItineraryODInfoRS> getItineraryODInfoRS(Long bookingId) {
        List<ItineraryODInfoTO> itineraryODInfoTOS = bookingDetailNQ.bookingOD(bookingId);
        List<ItineraryODInfoRS> itineraryODInfoRS = new ArrayList<>();

        for (ItineraryODInfoTO itineraryODInfo : itineraryODInfoTOS) {

            ItineraryODInfoRS itineraryOD = new ItineraryODInfoRS();
            BeanUtils.copyProperties(itineraryODInfo, itineraryOD);

            /**
             * Get Itinerary Segment Information
             */

            List<ItineraryODSegmentTO> itineraryODSegmentTOS = bookingDetailNQ.bookingODSegment(
                    itineraryODInfo.getId(),
                    environment.getProperty("spring.awsImageUrl.airline"),
                    headerBean.getLocalizationId()
            );
            List<ItineraryODSegmentRS> itineraryODSegmentRS = new ArrayList<>();

            for (ItineraryODSegmentTO itineraryODSegment : itineraryODSegmentTOS) {

                ItineraryODSegmentRS itineraryODSegmentObj = new ItineraryODSegmentRS();
                BeanUtils.copyProperties(itineraryODSegment, itineraryODSegmentObj);

                /**
                 * Get Stop information
                 */
                List<ItineraryStopInfoTO> itineraryStopInfoTO = bookingDetailNQ.bookingStopInfo(itineraryODSegment.getId());
                List<BookingStopInfoRS> bookingStopInfoRS = new ArrayList<>();

                for (ItineraryStopInfoTO stopInfoTO : itineraryStopInfoTO) {
                    BookingStopInfoRS bookingStopInfo = new BookingStopInfoRS();
                    BeanUtils.copyProperties(stopInfoTO , bookingStopInfo);
                    bookingStopInfoRS.add(bookingStopInfo);
                }

                itineraryODSegmentObj.setStopInfo(bookingStopInfoRS);
                itineraryODSegmentRS.add(itineraryODSegmentObj);

            }

            itineraryOD.setItinerarySegment(itineraryODSegmentRS);
            itineraryODInfoRS.add(itineraryOD);
        }
        return itineraryODInfoRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Price Breakdown
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return
     */
    public List<PriceBreakdownRS> getPriceBreakdownRS(Long bookingId) {
        List<PriceBreakDownTO> priceBreakDownTOS = bookingDetailNQ.itineraryPrice(bookingId);
        List<PriceBreakdownRS> priceBreakdownRSS = new ArrayList<>();
        for (PriceBreakDownTO priceBreakDown : priceBreakDownTOS) {

            PriceBreakdownRS priceBreakdownRS = new PriceBreakdownRS();
            BeanUtils.copyProperties(priceBreakDown, priceBreakdownRS);
            priceBreakdownRSS.add(priceBreakdownRS);

        }
        return priceBreakdownRSS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Baggage Information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return
     */
    public List<BaggageInfoRS> getBaggageInfoRS(Long bookingId) {
        List<BaggageInfoTO> baggageInfos = bookingDetailNQ.baggage(bookingId);
        List<BaggageInfoRS> baggageInfoRS = new ArrayList<>();
        for (BaggageInfoTO baggage : baggageInfos) {

            BaggageInfoRS baggageInfo = new BaggageInfoRS();
            BeanUtils.copyProperties(baggage, baggageInfo);

            /**
             * Get Baggage Allowance
             */
            if (baggage.getId() != null) {

                List<BaggageAllowanceTO> baggageAllowanceTOS = bookingDetailNQ.baggageAllowance(baggage.getId());
                List<BaggageAllowanceRS> baggageAllowanceRS = new ArrayList<>();

                for (BaggageAllowanceTO allowance : baggageAllowanceTOS) {
                    BaggageAllowanceRS baggageAllowance = new BaggageAllowanceRS();
                    BeanUtils.copyProperties(allowance, baggageAllowance);
                    baggageAllowanceRS.add(baggageAllowance);
                }

                baggageInfo.setBaggageAllowance(baggageAllowanceRS);

            }

            baggageInfoRS.add(baggageInfo);

        }
        return baggageInfoRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Ticket Information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return
     */
    public List<TicketInfoRS> getTicketInfoRS(Long bookingId) {
        List<TicketInfoTO> ticketInfoTOS = bookingDetailNQ.ticket(bookingId);
        List<TicketInfoRS> ticketInfoRS = new ArrayList<>();

        for (TicketInfoTO ticket : ticketInfoTOS) {
            TicketInfoRS ticketInfo = new TicketInfoRS();
            BeanUtils.copyProperties(ticket, ticketInfo);
            ticketInfoRS.add(ticketInfo);
        }
        return ticketInfoRS;
    }

}
