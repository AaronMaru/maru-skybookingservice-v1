package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.*;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.*;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightShoppingBean;
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

        List<BookingTO> bookingTO = bookingNQ.detailBooking("skyuser", id, skyuserId, companyId);

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

        List<BookingTO> bookingTO = bookingNQ.detailBooking("skyuser", id, skyuserId, companyId);

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
    public BookingDataPaginationRS getBooking(String stake) {

        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";

        String bookStatus = request.getParameter("bookStatus") != null ? request.getParameter("bookStatus") : "";
        String startRange = request.getParameter("startRange") != null ? request.getParameter("startRange") : "0";
        String endRange = request.getParameter("endRange") != null ? request.getParameter("endRange") : "0";
        String tripType = request.getParameter("tripType") != null ? request.getParameter("tripType") : "";
        String className = request.getParameter("className") != null ? request.getParameter("className") : "";
        String bookDate = request.getParameter("bookDate") != null ? request.getParameter("bookDate") : "";
        String paymentType = request.getParameter("paymentType") != null ? request.getParameter("paymentType") : "";
        String flyingFrom = request.getParameter("flyingFrom") != null ? request.getParameter("flyingFrom") : "";
        String flyingTo = request.getParameter("flyingTo") != null ? request.getParameter("flyingTo") : "";
        String bookByName = request.getParameter("bookByName") != null ? request.getParameter("bookByName") : "";

        Integer size = (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) ? Integer.valueOf(request.getParameter("size")) : 10;
        Integer page = (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) ? Integer.valueOf(request.getParameter("page")) - 1 : 0;

        String action = checkSearchOrFilter(keyword, bookStatus, startRange, endRange, tripType, className, bookDate,
                paymentType, flyingFrom, flyingTo, bookByName);

        BookingDataPaginationRS bookingData = new BookingDataPaginationRS();
        List<BookingRS> bookingRSList = bookings(keyword, bookStatus, startRange, endRange, tripType, className, bookDate,
                paymentType, flyingFrom, flyingTo, bookByName, action, stake, skyuserId, companyId, page, size);

        bookingData.setSize(size);
        bookingData.setPage(page);
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
    public List<BookingRS> bookings(String keyword, String bookStatus, String startRange, String endRange, String tripType,
                                    String className, String bookDate, String paymentType, String flyingFrom, String flyingTo,
                                    String bookByName, String action, String stake, Long skyuserId, Long companyId, Integer page, Integer size) {

        Page<BookingTO> bookingTO = bookingNQ.listBooking(keyword, bookStatus, startRange, endRange, tripType, className, bookDate,
                paymentType, flyingFrom, flyingTo, bookByName, action, stake, skyuserId, companyId, PageRequest.of(page, size));
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
    public String checkSearchOrFilter(String keyword, String bookStatus, String startRange, String endRange, String tripType,
                                      String className, String bookDate, String paymentType, String flyingFrom, String flyingTo,
                                      String bookByName) {

        String action = "search";
        if (request.getParameter("keyword") == null) {
            action = "filter";
        }
        if (request.getQueryString() == null) {
            action = "other";
        }
        if (bookStatus.equals("") && startRange.equals("0") && endRange.equals("0") && tripType.equals("") &&
                className.equals("") && bookDate.equals("") && paymentType.equals("") && flyingFrom.equals("") &&
                flyingTo.equals("") && bookByName.equals("") && keyword.equals("")) {

            action = "other";

        }

        return action;
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
                String statusOdSeg = (setBookingStatus(booking.getStatus()) != null) ? setBookingStatus(booking.getStatus()) : ((departDateTime.compareTo(now) > 0) ? "Upcoming" : "Completed");
                bookingOdSegRS.setStatus(statusOdSeg);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            HashMap<String, String> logo = flightBean.getAirlineInfoLogo(bookingOdSegTO.getAirlineCode());
            bookingOdSegRS.setAirlineLogo45(logo.get("logo45"));
            bookingOdSegRS.setAirlineLogo90(logo.get("logo90"));

            bookingOdSegRS.setArrDateTime(bookingOdSegTO.getArrDateTime().toString());
            bookingOdSegRS.setDepDateTime(bookingOdSegTO.getDepDateTime().toString());

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
