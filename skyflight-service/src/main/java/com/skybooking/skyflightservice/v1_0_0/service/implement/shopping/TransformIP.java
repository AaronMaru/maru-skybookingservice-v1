package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AircraftNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform.TransformSabre;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform.TransformSabreMerger;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class TransformIP implements TransformSV {

    public static final String TRANSFORM_CACHED_NAME = "shopping-transform";

    @Autowired
    private HazelcastInstance instance;

    @Autowired
    private FlightLocationNQ flightLocationNQ;

    @Autowired
    private AirlineNQ airlineNQ;

    @Autowired
    private AircraftNQ aircraftNQ;

    @Autowired
    private AppConfig appConfig;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * shopping transform
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingResponse
     * @return ShoppingTransformEntity
     */
    public ShoppingTransformEntity getShoppingTransform(ShoppingResponseEntity shoppingResponse) {

        if (!validateSabreResponse(shoppingResponse)) return null;

        var requestId = shoppingResponse.getId();
        var tripType = shoppingResponse.getQuery().getQuery().getTripType();

        var transform = new TransformSabreMerger(getTransformSabres(shoppingResponse.getResponses())).processingTransformer();
        transform.setRequestId(requestId);
        transform.setTrip(tripType);

        instance.getMap(TRANSFORM_CACHED_NAME).put(requestId, transform, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);

        return transform;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * validate response data from sabre
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingResponse
     * @return boolean
     */
    private boolean validateSabreResponse(ShoppingResponseEntity shoppingResponse) {

        try {
            var responses = shoppingResponse.getResponses();

            if (responses == null) return false;

            var valid = true;

            for (SabreBargainFinderRS sabreBargainFinderRS : responses) {
                var itineraryCount = sabreBargainFinderRS.getItineraryResponse().getStatistics().getItineraryCount();
                if (itineraryCount == 0) {
                    valid = false;
                }
            }

            return valid;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get transform sabre response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param responses
     * @return FastList
     */
    private FastList<TransformSabre> getTransformSabres(List<SabreBargainFinderRS> responses) {

        FastList<TransformSabre> transformSabres = FastList.newList();
        FastList
            .newList(responses)
            .forEachWithIndex((response, responseIndex) -> response
                .getItineraryResponse()
                .getItineraryGroups()
                .stream()
                .findFirst()
                .ifPresent(itineraryGroupType -> {
                    itineraryGroupType
                        .getGroupDescription()
                        .getLegDescriptions()
                        .stream()
                        .findFirst()
                        .ifPresent(legDescription ->
                            transformSabres.add(new TransformSabre(responseIndex, legDescription.getDepartureDate(), response))
                        );
                }));

        return transformSabres;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @param locale
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetail(ShoppingTransformEntity source, long locale) {

        if (source == null) return null;

        var airlines = source.getAirlines();
        var aircrafts = source.getAircrafts();
        var locations = source.getLocations();

        for (Airline airline : airlines) {

            var airlineTO = airlineNQ.getAirlineInformation(airline.getCode(), locale);

            if (airlineTO != null) {

                airline.setName(airlineTO.getAirbus());
                airline.setOperatingBy(airlineTO.getAirline());
                airline.setUrl90(appConfig.getAIRLINE_LOGO_PATH() + "/90/" + airlineTO.getLogo());
                airline.setUrl45(appConfig.getAIRLINE_LOGO_PATH() + "/45/" + airlineTO.getLogo());

            }

        }

        for (Aircraft aircraft : aircrafts) {
            var aircraftTO = aircraftNQ.getAircraftInformation(aircraft.getCode(), locale);

            if (aircraftTO != null) {
                aircraft.setName(aircraftTO.getAircraft());
            }
        }

        for (Location location : locations) {

            var locationTO = flightLocationNQ.getFlightLocationInformation(location.getCode(), locale);

            if (locationTO != null) {

                location.setAirport(locationTO.getAirport());
                location.setCity(locationTO.getCity());
                location.setLatitude(locationTO.getLatitude().doubleValue());
                location.setLongitude(locationTO.getLongitude().doubleValue());

            }

        }

        source.setAirlines(airlines);
        source.setAircrafts(aircrafts);
        source.setLocations(locations);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data with markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @param markup
     * @param currency
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetailMarkup(ShoppingTransformEntity source, double markup, String currency) {

        if (source == null) return null;

        var prices = source.getPrices();

        var formatter = NumberFormat.getInstance(new Locale("en"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        for (PriceDetail price : prices) {

            var total = 0.0;
            var average = 0.0;
            var passengers = 0;

            for (Price detail : price.getDetails()) {

                var baseFareMarkup = formatter.format(detail.getBaseFare().doubleValue() + (detail.getBaseFare().doubleValue() * markup));
                var taxMarkup = formatter.format(detail.getTax().doubleValue() + (detail.getTax().doubleValue() * markup));

                detail.setBaseFare(new BigDecimal(baseFareMarkup));
                detail.setTax(new BigDecimal(taxMarkup));

                total = (detail.getTax().add(detail.getBaseFare()).doubleValue() * detail.getQuantity()) + total;

                passengers += detail.getQuantity();
            }

            average = total / passengers;

            price.setTotal(NumberFormatter.amount(total));
            price.setTotalAvg(NumberFormatter.amount(average));

        }

        source.setPrices(prices);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping data detail with filter lowest price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param source
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetailWithFilter(ShoppingTransformEntity source) {


        if (source == null) return null;

        var formatter = NumberFormat.getInstance(new Locale("en"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        // apply lowest price to airline
        List<Airline> airlines = source.getAirlines();

        // apply sorting and filter lowest price
        List<Itinerary> cheapest = source.getCheapest();

        cheapest
            .forEach(itinerary -> {

                var totalDirection = itinerary.getLegGroups().size();
                if (totalDirection > 0) {

                    var airlineCode = itinerary.getLegGroups().get(0).getAirline();

                    var lowestPrice = (Double) itinerary
                        .getLowest()
                        .stream()
                        .map(legId -> {

                            var legDetail = source.getLegs().stream().filter(leg -> leg.getId().equalsIgnoreCase(legId)).findFirst().get();
                            var priceDetail = source.getPrices().stream().filter(price -> price.getId().equalsIgnoreCase(legDetail.getPrice())).findFirst().get();

                            return priceDetail.getTotal();
                        }).mapToDouble(Double::doubleValue).sum();

                    lowestPrice = Double.parseDouble(formatter.format(lowestPrice));

                    for (Airline airline : airlines) {
                        if (airline.getCode().equalsIgnoreCase(airlineCode)) {

                            if (airline.getPrice() == 0) {
                                airline.setPrice(lowestPrice);
                            }

                            if (airline.getPrice() > lowestPrice) {
                                airline.setPrice(lowestPrice);
                            }

                            break;
                        }
                    }
                }
            });

        source.setAirlines(airlines);
        source.setCheapest(cheapest);

        return source;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping data detail from cached by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformById(String id) {
        return (ShoppingTransformEntity) instance.getMap(TRANSFORM_CACHED_NAME).getOrDefault(id, null);
    }
}
