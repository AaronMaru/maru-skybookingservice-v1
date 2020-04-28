package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail.*;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingDetailSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking.FilterRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.destination.ArrivalRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.destination.DepartureRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return a booking
     * @Param id
     */
    public BookingDetailRS getBookingDetail(String bookingCode, SendBookingNoAuthRQ sendBookingNoAuthRQ) {

        String userType;
        String role = "";
        Long userId;
        Long companyId = 0l;
        if (sendBookingNoAuthRQ == null) {
            FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
            companyId = filterRQ.getCompanyHeaderId();
            userType = headerBean.getCompanyId(companyId);
            userId = filterRQ.getSkyuserId();
            role = filterRQ.getRole();
        } else {
            userType = sendBookingNoAuthRQ.getCompanyId() == null ? "skyuser" : "skyowner";
            userId = sendBookingNoAuthRQ.getSkyuserId().longValue();
            if (sendBookingNoAuthRQ.getCompanyId() != null) {
                companyId = sendBookingNoAuthRQ.getCompanyId().longValue();
                StakeholderUserHasCompanyEntity stakeholderUserHasCompanyEntity = companyHasUserRP
                        .findByStakeholderUserIdAndStakeholderCompanyId(userId, companyId);
                if (stakeholderUserHasCompanyEntity != null) {
                    role = stakeholderUserHasCompanyEntity.getSkyuserRole();
                }
            }
        }

        BookingKeyConstant constant = new BookingKeyConstant();
        BookingDetailTO bookingDetailTO = bookingDetailNQ.bookingDetail(
                userType,
                bookingCode,
                userId,
                companyId,
                role,
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
        List<BaggageInfoRS> baggageInfoRS = getBaggageInfoRS(bookingDetailTO.getBookingId());

        /**
         * Get Ticket Information
         */
        List<TicketInfoRS> ticketInfoRS = getTicketInfoRS(bookingDetailTO.getBookingId());

        /**
         * Object booking information
         */
        BookingInfoRS bookingInfoRS = new BookingInfoRS();
        BeanUtils.copyProperties(bookingDetailTO, bookingInfoRS);
        if (!bookingInfoRS.getUrlItinerary().equals("")) {
            bookingInfoRS.setUrlItinerary(environment.getProperty("spring.awsImageUrl.file.itinerary")+bookingInfoRS.getUrlItinerary());
            bookingInfoRS.setUrlReceipt(environment.getProperty("spring.awsImageUrl.file.receipt")+bookingInfoRS.getUrlReceipt());
        }

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

        List<PriceBreakdownRS> priceBreakdownRSS = getPriceBreakdownRS(bookingDetailTO.getBookingId());
        priceInfoRS.setPriceBreakdown(priceBreakdownRSS);

        /**
         * Get Itinerary Information
         */
        List<ItineraryODInfoRS> itineraryODInfoRS = getItineraryODInfoRS(bookingDetailTO.getBookingId(),
                bookingDetailTO.getStatusKey());

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
    public List<ItineraryODInfoRS> getItineraryODInfoRS(Integer bookingId, String StatusKey) {

        List<ItineraryODInfoTO> itineraryODInfoTOS = bookingDetailNQ.bookingOD(bookingId);
        List<ItineraryODInfoRS> itineraryODInfoRS = new ArrayList<>();
        BookingKeyConstant constant = new BookingKeyConstant();

        for (ItineraryODInfoTO itineraryODInfo : itineraryODInfoTOS) {

            ItineraryODInfoRS itineraryOD = new ItineraryODInfoRS();
            BeanUtils.copyProperties(itineraryODInfo, itineraryOD);

            /**
             * Get Itinerary Segment Information
             */

            List<ItineraryODSegmentTO> itineraryODSegmentTOS = bookingDetailNQ.bookingODSegment(itineraryODInfo.getId(),
                    environment.getProperty("spring.awsImageUrl.airline"), headerBean.getLocalizationId(),
                    constant.COMPLETED, constant.UPCOMING);
            List<ItineraryODSegmentRS> itineraryODSegmentRS = new ArrayList<>();

            for (ItineraryODSegmentTO itineraryODSegment : itineraryODSegmentTOS) {

                ItineraryODSegmentRS itineraryODSegmentObj = new ItineraryODSegmentRS();
                BeanUtils.copyProperties(itineraryODSegment, itineraryODSegmentObj);

                if (!itineraryODSegment.getStatus().equals(StatusKey)) {
                    itineraryODSegmentObj.setStatus(StatusKey);
                }

                /**
                 * Departure Object
                 */
                DepartureRS departureRS = getDepartureRS(itineraryODSegment);

                /**
                 * Arrival Object
                 */
                ArrivalRS arrivalRS = getArrivalRS(itineraryODSegment);

                /**
                 * Get Stop information
                 */
                List<ItineraryStopInfoTO> itineraryStopInfoTO = bookingDetailNQ
                        .bookingStopInfo(itineraryODSegment.getId(), headerBean.getLocalizationId());
                List<BookingStopInfoRS> bookingStopInfoRS = new ArrayList<>();

                for (ItineraryStopInfoTO stopInfoTO : itineraryStopInfoTO) {
                    BookingStopInfoRS bookingStopInfo = new BookingStopInfoRS();
                    BeanUtils.copyProperties(stopInfoTO, bookingStopInfo);
                    bookingStopInfo.setDuration(Integer.valueOf(stopInfoTO.getDuration()));
                    bookingStopInfoRS.add(bookingStopInfo);
                }

                itineraryODSegmentObj.setDepartureInfo(departureRS);
                itineraryODSegmentObj.setArrivalInfo(arrivalRS);
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
     * Get departure information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraryODSegment
     * @return
     */
    public DepartureRS getDepartureRS(ItineraryODSegmentTO itineraryODSegment) {
        DepartureRS departureRS = new DepartureRS();
        departureRS.setLocationCode(itineraryODSegment.getDepartureLocationCode());
        departureRS.setAirportName(itineraryODSegment.getDepartureAirportName());
        departureRS.setCity(itineraryODSegment.getDepartureCity());
        departureRS.setCountry(itineraryODSegment.getDepartureCountry());
        departureRS.setLatitude(itineraryODSegment.getDepartureLatitude());
        departureRS.setLongitude(itineraryODSegment.getDepartureLongitude());
        departureRS.setDate(itineraryODSegment.getDepartureDate());
        departureRS.setTerminal(itineraryODSegment.getDepartureTerminal());
        return departureRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Arrival information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraryODSegment
     * @return
     */
    public ArrivalRS getArrivalRS(ItineraryODSegmentTO itineraryODSegment) {
        ArrivalRS arrivalRS = new ArrivalRS();
        arrivalRS.setLocationCode(itineraryODSegment.getArrivalLocationCode());
        arrivalRS.setAirportName(itineraryODSegment.getArrivalAirportName());
        arrivalRS.setCity(itineraryODSegment.getArrivalCity());
        arrivalRS.setCountry(itineraryODSegment.getArrivalCountry());
        arrivalRS.setLatitude(itineraryODSegment.getArrivalLatitude());
        arrivalRS.setLongitude(itineraryODSegment.getArrivalLongitude());
        arrivalRS.setDate(itineraryODSegment.getArrivalDate());
        arrivalRS.setTerminal(itineraryODSegment.getArrivalTerminal());
        return arrivalRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Price Breakdown
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingId
     * @return
     */
    public List<PriceBreakdownRS> getPriceBreakdownRS(Integer bookingId) {
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
    public List<BaggageInfoRS> getBaggageInfoRS(Integer bookingId) {
        List<BaggageInfoTO> baggageInfos = bookingDetailNQ.baggage(bookingId);

        List<BaggageInfoRS> baggageInfoRS = new ArrayList<>();

        for (BaggageInfoTO baggage : baggageInfos) {

            BaggageInfoRS baggageInfo = new BaggageInfoRS();
            BeanUtils.copyProperties(baggage, baggageInfo);

            if (!baggage.getSegment().isEmpty()) {
                var cities = Arrays.stream(baggage.getSegment().split(",")).map(segment -> Arrays
                        .stream(segment.split("-"))
                        .map(it -> bookingDetailNQ.baggageLocation(it.trim(), headerBean.getLocalizationId()).getCity())
                        .collect(Collectors.joining("-"))).collect(Collectors.joining(", "));

                baggageInfo.setSegment(cities);
            }

            /**
             * Get Baggage Allowance
             */
            if (baggage.getId() != null) {

                List<BaggageAllowanceTO> baggageAllowanceTOS = bookingDetailNQ.baggageAllowance(baggage.getId());
                List<BaggageAllowanceRS> baggageAllowanceRS = new ArrayList<>();

                for (BaggageAllowanceTO allowance : baggageAllowanceTOS) {
                    BaggageAllowanceRS baggageAllowance = new BaggageAllowanceRS();
                    var piece = false;
                    BeanUtils.copyProperties(allowance, baggageAllowance);
                    if (allowance.getPiece() > 0) {
                        piece = true;
                    }
                    baggageAllowance.setPiece(piece);
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
    public List<TicketInfoRS> getTicketInfoRS(Integer bookingId) {
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
