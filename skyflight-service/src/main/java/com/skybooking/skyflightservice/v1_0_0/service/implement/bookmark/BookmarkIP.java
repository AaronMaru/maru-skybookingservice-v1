package com.skybooking.skyflightservice.v1_0_0.service.implement.bookmark;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.constant.UserConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark.FlightSaveOriginDestinationEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark.FlightSavesEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Leg;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.LegSegmentDetail;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.repository.bookmark.FlightSaveOriginDestinationRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.bookmark.FlightSavesRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.bookmark.BookmarkSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkAirline;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.service.model.bookmark.BookmarkCreateRS;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.CalculatorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.shopping.ShoppingUtils;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookmarkIP implements BookmarkSV {

    @Autowired
    private QuerySV querySV;

    @Autowired
    private DetailSV detailSV;

    @Autowired
    private FlightSavesRP flightSavesRP;

    @Autowired
    private FlightSaveOriginDestinationRP flightSaveOriginDestinationRP;

    @Autowired
    private AirlineNQ airlineNQ;

    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private ShoppingUtils shoppingUtils;

    @Autowired
    private MarkupNQ markupNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * find all bookmark that saved by user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookmarkCreateRQ
     * @param authenticationMetaTA
     * @return List
     */
    @Override
    public List<FlightSavesEntity> findAll(BookmarkCreateRQ bookmarkCreateRQ, UserAuthenticationMetaTA authenticationMetaTA) {

        var query = querySV.flightShoppingById(bookmarkCreateRQ.getRequest()).getQuery();

        var directions = query
            .getLegs()
            .stream()
            .map(flightLeg -> flightLeg.getOrigin().concat("-").concat(flightLeg.getDestination()))
            .collect(Collectors.joining("~"));

        var flightDepartureDate = query
            .getLegs()
            .stream()
            .map(flightLeg -> flightLeg.getDate().format(DateTimeFormatter.ofPattern("yyMMdd")))
            .findFirst()
            .get();

        try (Stream<FlightSavesEntity> foundBookmarkFlights = flightSavesRP.findAllByUserId(authenticationMetaTA.getStakeholderId())) {

            return foundBookmarkFlights.filter(flightSavesEntity -> {

                var tagDirection = flightSavesEntity.getTagId().split("@")[1];
                var departureDate = flightSavesEntity.getTagId().split(":")[3];
                var operatedAirline = flightSavesEntity.getTagId().split("@")[2];
                var operatedFlightTypeAirline = flightSavesEntity.getTagId().split(":")[0];

                var bookmarkedAirline = bookmarkCreateRQ.getItinerary().split("@")[2];
                var flightTypeAirline = bookmarkCreateRQ.getItinerary().split(":")[0];

                boolean isBookmarkFlightDirectionAndType = directions.equalsIgnoreCase(tagDirection) && operatedFlightTypeAirline.equalsIgnoreCase(flightTypeAirline) && departureDate.equals(flightDepartureDate);

                if (authenticationMetaTA.getCompanyId() != null) {

                    if (flightSavesEntity.getTripType().equalsIgnoreCase(query.getTripType().toString()) && flightSavesEntity.getClassCode().equalsIgnoreCase(query.getClassType()) && flightSavesEntity.getCompanyId().equals(authenticationMetaTA.getCompanyId())) {
                        if (isBookmarkFlightDirectionAndType) {
                            return operatedAirline.equalsIgnoreCase(bookmarkedAirline);
                        }
                    }

                } else {

                    if (flightSavesEntity.getCompanyId().equals(0)) {
                        if (flightSavesEntity.getTripType().equalsIgnoreCase(query.getTripType().toString()) && flightSavesEntity.getClassCode().equalsIgnoreCase(query.getClassType())) {
                            if (isBookmarkFlightDirectionAndType) {
                                return operatedAirline.equalsIgnoreCase(bookmarkedAirline);
                            }
                        }
                    }

                }

                return false;

            }).collect(Collectors.toList());
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete bookmark and bookmark detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itinerary
     * @param bookmarks
     * @return boolean
     */
    @Override
    public BookmarkCreateRS delete(String itinerary, List<FlightSavesEntity> bookmarks) {

        bookmarks
            .stream()
            .map(FlightSavesEntity::getId)
            .forEach(bookmarkId -> {

                flightSaveOriginDestinationRP.deleteInBatch(flightSaveOriginDestinationRP.findAllByFlightSaveId(bookmarkId).collect(Collectors.toList()));

                flightSavesRP.deleteById(bookmarkId);

            });

        return new BookmarkCreateRS(itinerary, "DELETED");

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create or remove favorite flight bookmark
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookmark
     * @param metadata
     * @return boolean
     */
    @Override
    @Transactional
    public BookmarkCreateRS create(BookmarkCreateRQ bookmark, UserAuthenticationMetaTA metadata) {

        try {
            var itinerary = detailSV.getItineraryDetail(bookmark.getRequest(), bookmark.getItinerary());

            if (itinerary == null) return null;


            // toggle to delete
            var bookmarks = this.findAll(bookmark, metadata);

            if (!bookmarks.isEmpty()) {
                return this.delete(bookmark.getItinerary(), bookmarks);
            }

            // toggle to create
            var legs = itinerary
                .getLowest()
                .stream()
                .map(leg -> detailSV.getLegDetail(bookmark.getRequest(), leg))
                .collect(Collectors.toList());

            var multiAirline = legs.stream().anyMatch(Leg::isMultiAir);
            var multiAirlineLogo = appConfig.getAIRLINE_LOGO_PATH() + "/air_multiple.png";

            var query = querySV.flightShoppingById(bookmark.getRequest()).getQuery();

            var markUp = shoppingUtils.getUserMarkupPrice(metadata, query.getClassType());
            var paymentMarkup = markupNQ.getGeneralMarkupPayment();

            var baseFare = NumberFormatter.trimAmount(bookingUtility.getBookingTotalAmount(bookmark.getRequest(), itinerary.getLowest().toArray(new String[0])));
            var totalMarkupAmount = NumberFormatter.trimAmount(bookingUtility.getBookingTotalMarkupAmount(baseFare.doubleValue(), markUp.getMarkup().doubleValue()));
            var totalBaseFareMarkupAmount = NumberFormatter.trimAmount(baseFare).add(totalMarkupAmount);
            var totalPaymentMarkupAmount = CalculatorUtils.getAmountPercentage(totalBaseFareMarkupAmount, paymentMarkup.getMarkup());

            var totalAmount = NumberFormatter.trimAmount(baseFare.add(totalMarkupAmount).add(totalPaymentMarkupAmount));

            var flightSavedEntity = new FlightSavesEntity();

            flightSavedEntity.setTripType(query.getTripType().toString());
            flightSavedEntity.setClassCode(query.getClassType().toUpperCase());
            flightSavedEntity.setClassName(bookingUtility.getFlightClassType(query.getClassType()));
            flightSavedEntity.setAdult(query.getAdult());
            flightSavedEntity.setChild(query.getChild());
            flightSavedEntity.setInfant(query.getInfant());

            flightSavedEntity.setAmount(totalAmount);
            flightSavedEntity.setDecimalPlaces(2);
            flightSavedEntity.setUserId(metadata.getStakeholderId());
            flightSavedEntity.setTagId(bookmark.getItinerary());
            flightSavedEntity.setBaseFare(baseFare);

            flightSavedEntity.setStopSearch(0);

            if (multiAirline) {
                flightSavedEntity.setMultipleAirStatus(1);
                flightSavedEntity.setMultipleAirLogo90(multiAirlineLogo);
            } else {
                flightSavedEntity.setMultipleAirStatus(0);
                flightSavedEntity.setMultipleAirLogo90("");
            }

            flightSavedEntity.setStatus(0);

            if (UserConstant.SKYOWNER.equalsIgnoreCase(metadata.getUserType())) {
                flightSavedEntity.setCompanyId(metadata.getCompanyId());
            } else {

                // TODO: column company can not store null and default value is zero.
                flightSavedEntity.setCompanyId(0);
            }

            flightSavedEntity.setCurrencyCode("USD");

            var bookmarkedFlight = flightSavesRP.save(flightSavedEntity);

            // insert data into flight saved origin destination tables
            legs
                .forEach(leg -> {

                    var segments = FastList.newList(leg.getSegments());

                    var stop = leg.getStops();

                    var firstSegmentDetail = segments.getFirst();
                    var firstSegment = detailSV.getSegmentDetail(bookmark.getRequest(), firstSegmentDetail.getSegment());
                    var firstAirline = airlineNQ.getAirlineInformation(firstSegment.getAirline(), 1);

                    var departure = flightLocationNQ.getFlightLocationInformation(leg.getDeparture(), 1);
                    var arrival = flightLocationNQ.getFlightLocationInformation(leg.getArrival(), 1);

                    var odEntity = new FlightSaveOriginDestinationEntity();

                    odEntity.setOLocation(departure.getCode());
                    odEntity.setOLocationName(departure.getCity());
                    odEntity.setDDateTime(DateUtility.convertZonedDateTimeToDateTime(leg.getDepartureTime()));

                    odEntity.setDLocation(arrival.getCode());
                    odEntity.setDLocationName(arrival.getCity());
                    odEntity.setADateTime(DateUtility.convertZonedDateTimeToDateTime(leg.getArrivalTime()));

                    odEntity.setAirlineCode(firstAirline.getCode());
                    odEntity.setAirlineName(firstAirline.getAirbus());
                    odEntity.setAirlineLogo45(appConfig.getAIRLINE_LOGO_PATH() + "/45/" + firstAirline.getLogo());
                    odEntity.setAirlineLogo90(appConfig.getAIRLINE_LOGO_PATH() + "/90/" + firstAirline.getLogo());

                    odEntity.setElapsedTime(Long.valueOf(leg.getDuration()).intValue());

                    odEntity.setFlightSaveId(bookmarkedFlight.getId());
                    odEntity.setUserId(metadata.getUserId());
                    odEntity.setStop(Long.valueOf(stop).intValue());

                    flightSaveOriginDestinationRP.save(odEntity);

                });

            return new BookmarkCreateRS(bookmark.getItinerary(), "CREATED");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list favorite flight based on flight shopping request
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShoppingRQ
     * @param userAuthenticationMetadata
     * @return List
     */
    @Override
    @Transactional
    public List<BookmarkAirline> get(FlightShoppingRQ flightShoppingRQ, UserAuthenticationMetaTA userAuthenticationMetadata) {

        try (Stream<FlightSavesEntity> foundBookmarkStream = flightSavesRP.findAllByUserId(userAuthenticationMetadata.getStakeholderId())) {

            var airlines = foundBookmarkStream
                .filter(requestHasBookmarked(flightShoppingRQ, userAuthenticationMetadata.getCompanyId()))
                .map(flightSavesEntity -> {

                    var itineraryType = flightSavesEntity.getTagId().split(":")[0].equalsIgnoreCase("1");
                    var airline = flightSavesEntity.getTagId().split("@")[2];
                    return new BookmarkAirline(airline, itineraryType);

                })
                .distinct()
                .collect(Collectors.toList());

            if (!airlines.isEmpty()) {
                return airlines;
            }

        }

        return Collections.emptyList();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * predicate and filter shopping request in saved flight records
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @param companyId
     * @return boolean
     */
    private Predicate<FlightSavesEntity> requestHasBookmarked(FlightShoppingRQ shoppingRQ, Integer companyId) {

        return bookmark -> {

            var flightSchedules = shoppingRQ
                .getLegs()
                .stream()
                .map(leg -> leg.getDate().format(DateTimeFormatter.ofPattern("yyMMdd")))
                .findFirst()
                .get();

            if (companyId != null) {

                if (bookmark.getTripType().equalsIgnoreCase(shoppingRQ.getTripType().toString()) && bookmark.getClassCode().equalsIgnoreCase(shoppingRQ.getClassType()) && bookmark.getCompanyId().equals(companyId)) {

                    var directions = shoppingRQ.getLegs().stream().map(flightLegRQ -> flightLegRQ.getDeparture().concat("-").concat(flightLegRQ.getArrival())).collect(Collectors.joining("~"));
                    var tagDirection = bookmark.getTagId().split("@")[1];
                    var departureDate = bookmark.getTagId().split(":")[3];

                    return directions.equalsIgnoreCase(tagDirection) && departureDate.equalsIgnoreCase(flightSchedules);
                }

            } else {

                if (bookmark.getCompanyId().equals(0)) {
                    if (bookmark.getTripType().equalsIgnoreCase(shoppingRQ.getTripType().toString()) && bookmark.getClassCode().equalsIgnoreCase(shoppingRQ.getClassType())) {

                        var directions = shoppingRQ.getLegs().stream().map(flightLegRQ -> flightLegRQ.getDeparture().concat("-").concat(flightLegRQ.getArrival())).collect(Collectors.joining("~"));
                        var tagDirection = bookmark.getTagId().split("@")[1];
                        var departureDate = bookmark.getTagId().split(":")[3];


                        return directions.equalsIgnoreCase(tagDirection) && departureDate.equalsIgnoreCase(flightSchedules);
                    }
                }

            }

            return false;
        };
    }
}
