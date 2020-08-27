package com.skybooking.skyflightservice.v1_0_0.service.implement.flight;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight.FlightInfoSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.ShareFlightRS;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.transformer.Share.LegTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.ShareFlightRQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.CalculatorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyflightservice.v1_0_0.util.email.EmailBean;
import com.skybooking.skyflightservice.v1_0_0.util.shopping.ShoppingUtils;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightInfoIP implements FlightInfoSV {

    @Autowired
    private FlightInfoNQ flightInfoNQ;

    @Autowired
    Environment environment;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private ShoppingUtils shoppingUtils;

    @Autowired
    private QuerySV querySV;

    @Autowired
    private MarkupNQ markupNQ;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AirlineNQ airlineNQ;

    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private EmailBean emailBean;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get config from env
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param Map<String, String>
     * @Return Properties
     */
    public Session getConfig() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        return session;
    }


    @Override
    public List<FlightInfoTO> getFlightInfoEnabled() {
        return flightInfoNQ.getFlightInfoShoppingEnabled();
    }

    @Override
    public ShareFlightRS sharingFlight(ShareFlightRQ shareFlightRQ, UserAuthenticationMetaTA metadata) {

        var itinerary = detailSV.getItineraryDetail(shareFlightRQ.getRequest(), shareFlightRQ.getItinerary());

        if (itinerary != null) {

            var legs = itinerary
                    .getLowest()
                    .stream()
                    .map(leg -> detailSV.getLegDetail(shareFlightRQ.getRequest(), leg))
                    .collect(Collectors.toList());

            var multiAirlineLogo = appConfig.getAIRLINE_LOGO_PATH() + "/air_multiple.png";

            var query = querySV.flightShoppingById(shareFlightRQ.getRequest()).getQuery();

            var paymentMarkup = markupNQ.getGeneralMarkupPayment();

            var markUp = shoppingUtils.getUserMarkupPrice(metadata, query.getClassType());

            var baseFare = NumberFormatter.trimAmount(bookingUtility.getBookingTotalAmount(shareFlightRQ.getRequest(),
                    itinerary.getLowest().toArray(new String[0])));
            var totalMarkupAmount = NumberFormatter.trimAmount(bookingUtility.getBookingTotalMarkupAmount(baseFare.doubleValue(), markUp.getMarkup().doubleValue()));
            var totalBaseFareMarkupAmount = NumberFormatter.trimAmount(baseFare).add(totalMarkupAmount);
            var totalPaymentMarkupAmount = CalculatorUtils.getAmountPercentage(totalBaseFareMarkupAmount, paymentMarkup.getMarkup());

            var totalAmount = NumberFormatter.trimAmount(baseFare.add(totalMarkupAmount).add(totalPaymentMarkupAmount));

            List<LegTF> legTFList = new ArrayList<>();
            legs.forEach(leg -> {

                var segments = FastList.newList(leg.getSegments());
                var firstSegmentDetail = segments.getFirst();
                var firstSegment = detailSV.getSegmentDetail(shareFlightRQ.getRequest(), firstSegmentDetail.getSegment());

                var departure = flightLocationNQ.getFlightLocationInformation(leg.getDeparture(), 1);
                var firstAirline = airlineNQ.getAirlineInformation(firstSegment.getAirline(), 1);
                var arrival = flightLocationNQ.getFlightLocationInformation(leg.getArrival(), 1);


                LegTF legTF = new LegTF();
                legTF.setDepartureCode(departure.getCode());
                legTF.setDepartureCity(departure.getCity());
                legTF.setDepartureDate(DateUtility.convertZonedDateTimeToDateTime(leg.getDepartureTime()));

                legTF.setArrivalCode(arrival.getCode());
                legTF.setArrivalCity(arrival.getCity());
                legTF.setArrivalDate(DateUtility.convertZonedDateTimeToDateTime(leg.getArrivalTime()));
                legTF.setDuration(dateTimeBean.convertNumberToHour((int) leg.getDuration()));
                legTF.setStop(leg.getStops());

                legTF.setAirLineCode(firstAirline.getCode());
                legTF.setAirLineName(firstAirline.getAirbus());
                legTF.setAirLineLogo(appConfig.getAIRLINE_LOGO_PATH() + "/90/" + firstAirline.getLogo());

                legTFList.add(legTF);
            });

            Map<String, Object> mailTemplateData = new HashMap<>();
            mailTemplateData.put("requestDate", shareFlightRQ);
            mailTemplateData.put("link", shareFlightRQ.getLink());
            mailTemplateData.put("description", shareFlightRQ.getDescription());

            mailTemplateData.put("totalPrice", totalAmount);
            mailTemplateData.put("multiAirlineLogo", multiAirlineLogo);
            mailTemplateData.put("legs", legTFList);
            mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

            emailBean.email(mailTemplateData);

            ShareFlightRS shareFlightRS = new ShareFlightRS();
            shareFlightRS.setStatus("shared");

            return shareFlightRS;
        }

        return null;
    }

}
