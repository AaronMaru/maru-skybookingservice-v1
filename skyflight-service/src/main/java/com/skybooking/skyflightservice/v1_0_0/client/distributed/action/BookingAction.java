package com.skybooking.skyflightservice.v1_0_0.client.distributed.action;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.config.company.Company;
import com.skybooking.skyflightservice.config.passenger.PassengerCode;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BCreateDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BPassengerDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BSegmentDRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.booking.DBookingRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.security.token.DSTokenHolder;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.RevalidateFlight;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingAction {

    @Autowired
    private DSTokenHolder dsTokenHolder;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient client;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private RevalidateFlight revalidateFlight;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Create Passenger Name Records to Supplier
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return DBookingRS
     */
    public DBookingRS create(BCreateRQ request) {

        var requestId = request.getRequestId();
        var seats = 0;
        List<BSegmentDRQ> segments = new ArrayList<>();
        List<BPassengerDRQ> passengers = new ArrayList<>();
        for (BPassengerRQ passengerRQ: request.getPassengers()) {
            if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.ADULT) {
                seats++;
            } else if (PassengerUtil.type(passengerRQ.getBirthDate()) == PassengerCode.CHILD) {
                seats++;
            }

            BPassengerDRQ passenger = new BPassengerDRQ();
            passenger.setFirstName(passengerRQ.getFirstName());
            passenger.setLastName(passengerRQ.getLastName());
            passenger.setGender(passengerRQ.getGender());
            passenger.setBirthDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getBirthDate()));
            passenger.setIdType(passengerRQ.getIdType());
            passenger.setIdNumber(passengerRQ.getIdNumber());
            passenger.setExpireDate(DatetimeFormat.parse("yyyy-MM-dd", passengerRQ.getExpireDate()));
            passenger.setNationality(passengerRQ.getNationality());
            passengers.add(passenger);
        }

        for (String leg: request.getLegIds()) {
            var legDetail = detailSV.getLegDetail(requestId, leg);

            for (LegSegmentDetail legSegment: legDetail.getSegments()) {
                segments.add(revalidateFlight.setDSegment(detailSV.getSegmentDetail(requestId, legSegment.getSegment()), seats+""));
            }
        }

        BCreateDRQ requestBody = new BCreateDRQ();
        requestBody.setCompany(new Company());
        requestBody.setContact(request.getContact());
        requestBody.setSegments(segments);
        requestBody.setPassengers(passengers);

        return client.mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/booking/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(DBookingRS.class).block();
    }
}
