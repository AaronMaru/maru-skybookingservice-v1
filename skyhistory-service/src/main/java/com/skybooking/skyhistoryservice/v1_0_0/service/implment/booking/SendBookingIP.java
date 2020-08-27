package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking.BookingEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.company.CompanyDocsRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.user.StakeHolderUserRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingDetailSV;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.mail.*;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.PrintRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.BookingDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.email.EmailBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.skybooking.skyhistoryservice.constant.MailStatusConstant.*;

@Service
public class SendBookingIP implements SendBookingSV {

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private CompanyDocsRP companyDocsRP;

    @Autowired
    private Environment environment;

    @Autowired
    private BookingDetailSV bookingDetailSV;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private HttpServletRequest request;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendBookingInfo(SendBookingPDFRQ sendBookingPDFRQ, String pdfTemplate, String Label) {

        String locale = headerBean.getLocalization();

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingPDFRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("no_bk", null);
        }

        String template = YOUR_FLIGHT_TICKET;
        if (pdfTemplate.equals("receipt")) {
            template = FORWARD_E_RECEIPT;
        } else if (pdfTemplate.equals("itinerary")) {
            template = FORWARD_FLIGHT_TICKET;
        }

        BookingDetailRS bookingEmailDetailRS = bookingDetailSV.getBookingDetail(booking.getBookingCode(), null);

        BookingDetailTF mailBookingDetail = this.mailBookingDetail(bookingEmailDetailRS);

        Map<String, Object> mailData = emailBean.mailData(sendBookingPDFRQ.getEmail(), "Customer", 0,
                template, "booking-info", "api_receipt");
        mailData.put("data", mailBookingDetail);

        // apply locale
        mailData.put("pdfLang", locale);

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("label_" + pdfTemplate, emailBean.dataPdfTemplate(pdfTemplate, Label));
        pdfData.put("data_" + pdfTemplate, mailBookingDetail);

        // apply logo
        Integer companyId = request.getHeader("X-CompanyId") != null &&
                !request.getHeader("X-CompanyId").isEmpty() ?
                Integer.valueOf(request.getHeader("X-CompanyId")): 0;
        pdfData.put("logoPdf", this.embedLogo(companyId));

        List<String> sendData = Arrays.asList(pdfTemplate);
        pdfData.put("sendData", sendData);

        emailBean.sendEmailSMS("send-booking", mailData, pdfData);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt and itinerary
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendBookingInfo(SendBookingNoAuthRQ sendBookingNoAuthRQ) {
        String locale = headerBean.getLocalization();

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingNoAuthRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("no_bk", null);
        }

        StakeHolderUserEntity stakeHolderUserEntity = stakeHolderUserRP
                .findById(sendBookingNoAuthRQ.getSkyuserId().longValue()).orElse(null);

        String fullName = "";

        if (stakeHolderUserEntity != null) {
            fullName = stakeHolderUserEntity.getFirstName() + " " + stakeHolderUserEntity.getLastName();
        }

        BookingDetailRS bookingEmailDetailRS = bookingDetailSV.getBookingDetail(booking.getBookingCode(),
                sendBookingNoAuthRQ);

        BookingDetailTF mailBookingDetail = this.mailBookingDetail(bookingEmailDetailRS);

        Map<String, Object> mailData = emailBean.mailData(sendBookingNoAuthRQ.getEmail(), fullName, 0,
                YOUR_FLIGHT_TICKET, "booking-info", "api_receipt");
        mailData.put("data", mailBookingDetail);
        // apply locale
        mailData.put("pdfLang", locale);

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("label_receipt", emailBean.dataPdfTemplate("receipt", "api_receipt_pdf"));
        pdfData.put("data_receipt", mailBookingDetail);

        pdfData.put("label_itinerary", emailBean.dataPdfTemplate("itinerary", "api_itinerary_pdf"));
        pdfData.put("data_itinerary", mailBookingDetail);

        // apply logo
        pdfData.put("logoPdf", this.embedLogo(sendBookingNoAuthRQ.getCompanyId()));

        List<String> sendData = Arrays.asList("receipt", "itinerary");
        pdfData.put("sendData", sendData);

        emailBean.sendEmailSMS("send-booking", mailData, pdfData);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {
        String locale = headerBean.getLocalization();

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingPDFRQ.getBookingCode());
        if (booking == null) {
            throw new BadRequestException("no_bk", null);
        }

        BookingDetailRS bookingEmailDetailRS = bookingDetailSV.getBookingDetail(booking.getBookingCode(), null);

        BookingDetailTF mailBookingDetail = this.mailBookingDetail(bookingEmailDetailRS);

        Map<String, Object> mailData = emailBean.mailData(sendBookingPDFRQ.getEmail(), "Customer", 0,
                FLIGHT_BOOKING_SUCCESSFUL, "payment-success", "api_payment_succ");

        mailData.put("data", mailBookingDetail);

        // apply locale
        mailData.put("pdfLang", locale);

        emailBean.sendEmailSMS("send-payment", mailData, null);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendPaymentWithoutAuth(SendBookingNoAuthRQ sendBookingNoAuthRQ) {

        String locale = headerBean.getLocalization();

        StakeHolderUserEntity stakeHolderUserEntity = stakeHolderUserRP
                .findById(sendBookingNoAuthRQ.getSkyuserId().longValue()).orElse(null);

        String fullName = "";
        if (stakeHolderUserEntity != null) {
            fullName = stakeHolderUserEntity.getFirstName() + " " + stakeHolderUserEntity.getLastName();
        }

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingNoAuthRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("no_bk", null);
        }

        BookingDetailRS bookingEmailDetailRS = bookingDetailSV.getBookingDetail(booking.getBookingCode(),
                sendBookingNoAuthRQ);

        BookingDetailTF mailBookingDetail = this.mailBookingDetail(bookingEmailDetailRS);

        Map<String, Object> mailData = emailBean.mailData(sendBookingNoAuthRQ.getEmail(), fullName, 0,
                FLIGHT_BOOKING_SUCCESSFUL, PAYMENT_SUCCESS, API_PAYMENT_SUCCESS);

        mailData.put("data", mailBookingDetail);
        // apply locale
        mailData.put("pdfLang", locale);

        emailBean.sendEmailSMS("send-payment", mailData, null);

    }


    private String embedLogo(Integer companyId) {

        companyId = companyId == null ? 0 : companyId;

        if (companyId == 0) {
            return environment.getProperty("spring.awsImageUrl.skyUserLogo");
        }

        StakeholderCompanyDocsEntity stakeholderCompanyDocsEntity = companyDocsRP.getItineraryProfile(companyId);

        if (stakeholderCompanyDocsEntity != null) {
            return environment.getProperty("spring.awsImageUrl.companyProfile") + stakeholderCompanyDocsEntity.getFileName();
        } else {
            return environment.getProperty("spring.awsImageUrl.companyProfile") + "default.png";
        }
    }

    @Override
    public PrintRS
    printBooking(String bookingCode) {
        BookingEntity booking = bookingRP.findByBookingCode(bookingCode);

        if (booking == null) {
            throw new BadRequestException("no_bk", null);
        }

        PrintRS printRS = new PrintRS();

        printRS.setUrl(environment.getProperty("spring.awsImageUrl.file.itinerary") + booking.getItineraryPath() + "/" + booking.getItineraryFile());

        return printRS;
    }

    public BookingDetailTF mailBookingDetail(BookingDetailRS bookingEmailDetailRS) {

        BookingDetailTF bookingDetailTF = new BookingDetailTF();
        BeanUtils.copyProperties(bookingEmailDetailRS, bookingDetailTF);

        BookingInfoTF bookingInfoTF = new BookingInfoTF();
        BeanUtils.copyProperties(bookingEmailDetailRS.getBookingInfo(), bookingInfoTF);
        bookingInfoTF
                .setBookingDate(dateTimeBean.convertDateTime(bookingEmailDetailRS.getBookingInfo().getBookingDate()));

        List<ItineraryODInfoTF> itineraryODInfoTFList = new ArrayList<>();

        bookingEmailDetailRS.getItineraryInfo().forEach(itemItineraryInfo -> {

            ItineraryODInfoTF itineraryODInfoTF = new ItineraryODInfoTF();
            List<ItineraryODSegmentTF> itineraryODSegmentTFList = new ArrayList<>();

            itemItineraryInfo.getItinerarySegment().forEach(itemItinerarySegment -> {

                ItineraryODSegmentTF itineraryODSegmentTF = new ItineraryODSegmentTF();

                DepartureTF departureTF = new DepartureTF();
                BeanUtils.copyProperties(itemItinerarySegment.getDepartureInfo(), departureTF);
                departureTF.setDate(dateTimeBean.convertDateTime(itemItinerarySegment.getDepartureInfo().getDate()));

                ArrivalTF arrivalTF = new ArrivalTF();
                BeanUtils.copyProperties(itemItinerarySegment.getArrivalInfo(), arrivalTF);
                arrivalTF.setDate(dateTimeBean.convertDateTime(itemItinerarySegment.getArrivalInfo().getDate()));

                List<BookingStopInfoTF> bookingStopInfoTFList = new ArrayList<>();
                itemItinerarySegment.getStopInfo().forEach(itemStopInfo -> {

                    BookingStopInfoTF bookingStopInfoTF = new BookingStopInfoTF();
                    BeanUtils.copyProperties(itemStopInfo, bookingStopInfoTF);
                    bookingStopInfoTF
                            .setDurationHourMinute(dateTimeBean.convertNumberToHour(bookingStopInfoTF.getDuration()));

                    bookingStopInfoTFList.add(bookingStopInfoTF);
                });

                BeanUtils.copyProperties(itemItinerarySegment, itineraryODSegmentTF);
                itineraryODSegmentTF.setFlightNumber(itemItinerarySegment.getFlightNumber().toString());
                itineraryODSegmentTF.setDepartureInfo(departureTF);
                itineraryODSegmentTF.setArrivalInfo(arrivalTF);
                itineraryODSegmentTF
                        .setLayoverHourMinute(dateTimeBean.convertNumberToHour(itineraryODSegmentTF.getLayover()));
                itineraryODSegmentTF.setStopInfo(bookingStopInfoTFList);
                itineraryODSegmentTFList.add(itineraryODSegmentTF);
            });

            BeanUtils.copyProperties(itemItineraryInfo, itineraryODInfoTF);
            itineraryODInfoTF.setItinerarySegment(itineraryODSegmentTFList);
            itineraryODInfoTF.setElapsedHourMinute(dateTimeBean.convertNumberToHour(itemItineraryInfo.getElapsedTime()));
            itineraryODInfoTFList.add(itineraryODInfoTF);
        });

        bookingDetailTF.setBookingInfo(bookingInfoTF);
        bookingDetailTF.setItineraryInfo(itineraryODInfoTFList);

        return bookingDetailTF;
    }
}
