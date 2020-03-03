package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

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
     * Getting data bookings detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return a booking
     * @Param id
     */
    public BookingDetailRS getBookingDetail(Long id) {

        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        String stake = headerBean.getCompanyId(companyId);
        String role = (stake.equals("company")) ? jwtUtils.getClaim("userRole", String.class) : "";

        List<BookingTO> bookingTO = bookingNQ.detailBooking(stake, id, skyuserId, companyId, role);

        BookingDetailRS bookingDetailRS = new BookingDetailRS();

        if (bookingTO.size() == 0) {
            throw new NotFoundException("This url not found", "");
        }

        BeanUtils.copyProperties(bookingTO.get(0), bookingDetailRS);

        List<BookingOdRS> bookingOdRSList = bookingOD(bookingTO.get(0));
        List<BookingTicketRS> bookingTicketRSList = getAirTickets(id);
        List<BookingAirItinPriceRS> bookingAirItinPriceRSList = getAirItinPrice(id);

        bookingDetailRS.setBookingOd(bookingOdRSList);
        bookingDetailRS.setAirTickets(bookingTicketRSList);
        bookingDetailRS.setAirItinPrices(bookingAirItinPriceRSList);

        return bookingDetailRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings detail data to email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return a booking
     * @Param id
     */
    public BookingEmailDetailRS getBookingDetailEmail(Long id) {

        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        String stake = headerBean.getCompanyId(companyId);
        String role = (stake.equals("company")) ? jwtUtils.getClaim("userRole", String.class) : "";

        List<BookingTO> bookingTO = bookingNQ.detailBooking(stake, id, skyuserId,
                companyId, role);

        BookingEmailDetailRS bookingRS = new BookingEmailDetailRS();

        if (bookingTO.size() == 0) {
            throw new NotFoundException("No booking data found", null);
        }

        BeanUtils.copyProperties(bookingTO.get(0), bookingRS);

        List<BookingOdRS> bookingOdRSList = bookingOD(bookingTO.get(0));
        List<BookingTicketRS> bookingTicketRSList = getAirTickets(id);
        List<BookingAirItinPriceRS> bookingAirItinPriceRSList = getAirItinPrice(id);

        bookingRS.setBookingOd(bookingOdRSList);
        bookingRS.setAirTickets(bookingTicketRSList);
        bookingRS.setAirItinPrices(bookingAirItinPriceRSList);

        return bookingRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings detail data to email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return a booking
     * @Param id
     */
    public BookingEmailDetailRS getBookingDetailEmailWithoutAuth(Long id, SendBookingNoAuthRQ sendBookingNoAuthRQ) {

        if (sendBookingNoAuthRQ.getCompanyId() == null) {
            sendBookingNoAuthRQ.setCompanyId(0);
        }

        List<BookingTO> bookingTO = bookingNQ.detailBooking(
                (sendBookingNoAuthRQ.getCompanyId() == 0) ? "skyuser" : "company", id,
                sendBookingNoAuthRQ.getSkyuserId().longValue(), sendBookingNoAuthRQ.getCompanyId().longValue(), "");

        BookingEmailDetailRS bookingRS = new BookingEmailDetailRS();

        if (bookingTO.size() == 0) {
            throw new NotFoundException("This url not found", "");
        }

        BeanUtils.copyProperties(bookingTO.get(0), bookingRS);

        List<BookingOdRS> bookingOdRSList = bookingOD(bookingTO.get(0));
        List<BookingTicketRS> bookingTicketRSList = getAirTickets(id);
        List<BookingAirItinPriceRS> bookingAirItinPriceRSList = getAirItinPrice(id);

        bookingRS.setBookingOd(bookingOdRSList);
        bookingRS.setAirTickets(bookingTicketRSList);
        bookingRS.setAirItinPrices(bookingAirItinPriceRSList);

        return bookingRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting data bookings with pagination
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return list of bookings
     */
    public BookingDataPaginationRS getBooking(HttpServletRequest request) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
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
                headerBean.getCompanyId(filterRQ.getCompanyId()),
                PageRequest.of(filterRQ.getPage(), filterRQ.getSize() ) );

        List<BookingRS> bookingRSList = new ArrayList<>();

        for (BookingTO booking : bookingTO) {

            BookingRS bookingRS = new BookingRS();

            List<BookingOdRS> bookingOdRSList = bookingOD(booking);

            BeanUtils.copyProperties(booking, bookingRS);

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


        if (request.getParameter("keyword") != null) {
            return "search";
        }

        if (request.getParameter("bookStatus") != null) {
            return "filter";
        }

        if (request.getParameter("startRange") != null) {
            return "filter";
        }

        if (request.getParameter("endRange") != null) {
            return "filter";
        }

        if (request.getParameter("tripType") != null) {
            return "filter";
        }

        if (request.getParameter("className") != null) {
            return "filter";
        }

        if (request.getParameter("bookDate") != null) {
            return "filter";
        }

        if (request.getParameter("paymentType") != null) {
            return "filter";
        }

        if (request.getParameter("flyingFrom") != null) {
            return "filter";
        }

        if (request.getParameter("flyingTo") != null) {
            return "filter";
        }

        if (request.getParameter("bookByName") != null) {
            return "filter";
        }

        if (request.getParameter("keyword") != null) {
            return "filter";
        }

        return "other";

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
            BookingOdSegTO bookingOdSegTO = bookingNQ.bookingOdSeg(bookingOdTO.getId());
            BookingOdSegRS bookingOdSegRS = new BookingOdSegRS();

            BeanUtils.copyProperties(bookingOdSegTO, bookingOdSegRS);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date departDateTime = formatter.parse(bookingOdSegTO.getDepDateTime().toString());
                String statusOdSeg = (setBookingStatus(booking.getStatus()) != null)
                        ? setBookingStatus(booking.getStatus())
                        : ((departDateTime.compareTo(now) > 0) ? "Upcoming" : "Completed");
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

        if (status == cancel) {
            bookingStatus = "Cancelled";
        }
        if (status == failOne || status == failTwo) {
            bookingStatus = "Failed";
        }

        return bookingStatus;

    }


}
