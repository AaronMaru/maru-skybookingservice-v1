package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.constant.BookingKeyConstant;
import com.skybooking.skyhistoryservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.*;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.booking.FilterRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.*;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightShoppingBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BookingIP implements BookingSV {

    @Autowired
    private BookingNQ bookingNQ;

    @Autowired
    private HttpServletRequest request;

    private Long totals;

    @Autowired
    private FlightShoppingBean flightBean;

    @Autowired
    Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private DateTimeBean dateTimeBean;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings with pagination
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return list of bookings
     */
    public BookingDataPaginationRS getBooking(HttpServletRequest request) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());

        if (filterRQ.getTripType().equals(BookingKeyConstant.MULTICITY)) {
            filterRQ.setTripType("Other");
        }

        if (filterRQ.getTripType().equals(BookingKeyConstant.ROUND)) {
            filterRQ.setTripType("Return");
        }

        filterRQ.setAction(checkSearchOrFilter());

        BookingDataPaginationRS bookingData = new BookingDataPaginationRS();

        List<BookingRS> bookingRSList = bookings(filterRQ);

        bookingData.setSize(filterRQ.getSize());
        bookingData.setPage(filterRQ.getPage() + 1);
        bookingData.setTotals(totals);
        bookingData.setData(bookingRSList);

        return bookingData;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting a list of bookings
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return list of bookings
     * @Param keyword
     * @Param bookingStatus
     * @Param page
     * @Param size
     */
    public List<BookingRS> bookings(FilterRQ filterRQ) {

        Page<BookingTO> bookingTO = bookingNQ.listBooking(
                filterRQ.getSkystaffId(),
                filterRQ.getKeyword(),
                filterRQ.getBookStatus(),
                filterRQ.getStartRange(),
                filterRQ.getEndRange(),
                filterRQ.getTripType(),
                filterRQ.getClassName(),
                filterRQ.getBookDate(),
                filterRQ.getPaymentType(),
                filterRQ.getFlyingFrom(),
                filterRQ.getFlyingTo(),
                filterRQ.getBookByName(),
                filterRQ.getAction(),
                filterRQ.getRole(),
                filterRQ.getSkyuserId(),
                filterRQ.getCompanyId(),
                filterRQ.getCompleted(),
                filterRQ.getUpcoming(),
                filterRQ.getCancelled(),
                filterRQ.getFailed(),
                filterRQ.getOneway(),
                filterRQ.getRound(),
                filterRQ.getMulticity(),
                headerBean.getCompanyId(filterRQ.getCompanyId()),
                PageRequest.of(filterRQ.getPage(), filterRQ.getSize()));

        List<BookingRS> bookingRSList = new ArrayList<>();

        for (BookingTO booking : bookingTO) {

            BookingRS bookingRS = new BookingRS();

            List<BookingOdRS> bookingOdRSList = bookingOD(booking);

            BeanUtils.copyProperties(booking, bookingRS);
            bookingRS.setBookDate(dateTimeBean.convertDateTime(booking.getBookDate()));

            bookingRS.setSkyuserPhoto(environment.getProperty("spring.awsImageUrl.profile.url_small") + booking.getSkyuserPhoto());

            bookingRS.setBookingOd(bookingOdRSList);

            bookingRSList.add(bookingRS);

        }

        totals = bookingTO.getTotalElements();

        return bookingRSList;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Determine is search or filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     * @Param booking
     */
    public String checkSearchOrFilter() {

        if (request.getParameter("bookStatus") != null || request.getParameter("startRange") != null ||
            request.getParameter("endRange") != null || request.getParameter("tripType") != null ||
            request.getParameter("className") != null || request.getParameter("bookDate") != null ||
            request.getParameter("paymentType") != null || request.getParameter("flyingFrom") != null ||
            request.getParameter("flyingTo") != null || request.getParameter("bookByName") != null
        ) {

            return "FILTER";

        } else if(request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")) {

            return "SEARCH";

        }

        return "OTHER";

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Booking origin destination
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     * @Param booking
     */
    public List<BookingOdRS> bookingOD(BookingTO booking) {

        Date now = new Date();
        List<BookingOdTO> bookingOdTOList = bookingNQ.listBookingOd(booking.getBookingId());
        List<BookingOdRS> bookingOdRSList = new ArrayList<>();

        for (BookingOdTO bookingOdTO : bookingOdTOList) {
            BookingOdRS bookingOdRS = new BookingOdRS();
            BeanUtils.copyProperties(bookingOdTO, bookingOdRS);
            BookingOdSegTO bookingOdSegTO = bookingNQ.bookingOdSeg(bookingOdTO.getId(), headerBean.getLocalizationId());
            BookingOdSegRS bookingOdSegRS = new BookingOdSegRS();

            BeanUtils.copyProperties(bookingOdSegTO, bookingOdSegRS);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date departDateTime = formatter.parse(bookingOdSegTO.getDepDateTime().toString());
                String statusOdSeg = (setBookingStatus(booking.getStatus()) != null)
                        ? setBookingStatus(booking.getStatus())
                        : ((departDateTime.compareTo(now) > 0) ? BookingKeyConstant.UPCOMING : BookingKeyConstant.COMPLETED);
                bookingOdSegRS.setStatus(statusOdSeg);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            HashMap<String, String> logo = flightBean.getAirlineInfoLogo(bookingOdSegTO.getAirlineCode());
            bookingOdSegRS.setAirlineLogo45(logo.get("logo45"));
            bookingOdSegRS.setAirlineLogo90(logo.get("logo90"));

            bookingOdSegRS.setArrDateTime(dateTimeBean.convertDateTime(bookingOdSegTO.getArrDateTime()));
            bookingOdSegRS.setDepDateTime(dateTimeBean.convertDateTime(bookingOdSegTO.getDepDateTime()));

            bookingOdRS.setfSegs(bookingOdSegRS);

            bookingOdRSList.add(bookingOdRS);
        }

        return bookingOdRSList;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting a listing of Airtickets
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return BookingTicketRS
     * @Param bookingId
     */
    public List<BookingTicketRS> getAirTickets(Long bookingId) {

        List<BookingTicketsTQ> bookingTicketsTQList = bookingNQ.listTicket(bookingId);
        List<BookingTicketRS> bookingTicketRSList = new ArrayList<>();

        for (BookingTicketsTQ bookingTicketsTQ : bookingTicketsTQList) {
            BookingTicketRS bookingTicketRS = new BookingTicketRS();
            BeanUtils.copyProperties(bookingTicketsTQ, bookingTicketRS);

            BookingBaggageInfoRS bookingBaggageInfoRS = new BookingBaggageInfoRS();
            String passTypeCode = flightBean.getPassTypeCode(bookingTicketsTQ.getPassType());
            BookingBaggageInfoTQ bookingBaggageInfoTQ = bookingNQ.baggageInfo(bookingId, passTypeCode);

            BeanUtils.copyProperties(bookingBaggageInfoTQ, bookingBaggageInfoRS);

            bookingTicketRS.setBookingBaggageInfoRS(bookingBaggageInfoRS);
            bookingTicketRSList.add(bookingTicketRS);

        }

        return bookingTicketRSList;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting a listing of Airtickets
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return BookingAirItinPriceRS
     * @Param bookingId
     */
    public List<BookingAirItinPriceRS> getAirItinPrice(Long bookingId) {

        List<BookingAirItinPriceTQ> bookingAirItinPriceTQList = bookingNQ.listAirItinPrice(bookingId);
        List<BookingAirItinPriceRS> bookingAirItinPriceRSList = new ArrayList<>();

        for (BookingAirItinPriceTQ bookingAirItinPriceTQ : bookingAirItinPriceTQList) {

            BookingAirItinPriceRS bookingAirItinPriceRS = new BookingAirItinPriceRS();
            BeanUtils.copyProperties(bookingAirItinPriceTQ, bookingAirItinPriceRS);

            bookingAirItinPriceRSList.add(bookingAirItinPriceRS);
        }

        return bookingAirItinPriceRSList;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find status of booking cancel and fail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     * @Param status
     */
    public String setBookingStatus(int status) {

        String bookingStatus = null;

        int cancel = NumberUtils.toInt(environment.getProperty("spring.booking-status.payment.cancel"));
        int failOne = NumberUtils.toInt(environment.getProperty("spring.booking-status.ticket.fail"));
        int failTwo = NumberUtils.toInt(environment.getProperty("spring.booking-status.pnr.fail"));
        int failThree = NumberUtils.toInt(environment.getProperty("spring.booking-status.payment.processing"));

        if (status == cancel) {
            bookingStatus = BookingKeyConstant.CANCELLED;
        }
        if (status == failOne || status == failTwo || status == failThree) {
            bookingStatus = BookingKeyConstant.FAILED;
        }

        return bookingStatus;

    }


}
