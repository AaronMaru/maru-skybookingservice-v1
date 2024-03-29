package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.cabin.CabinEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.meal.MealEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AircraftNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.AirlineNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.FlightLocationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.repository.cabin.CabinRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.meal.MealRP;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform.TransformSabre;
import com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform.TransformSabreMerger;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.bookmark.BookmarkSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.currency.ExchangeCurrencyTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.CalculatorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.shopping.ShoppingUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransformIP implements TransformSV {

    public static final String TRANSFORM_CACHED_NAME = "shopping-transform";
    public static final String CLASS_OF_SERVICE_CACHED_NAME = "class-of-service";
    public static final String FARE_BASIS_CACHED_NAME = "fare-basis";

    private final HazelcastInstance instance;
    private final FlightLocationNQ flightLocationNQ;
    private final AirlineNQ airlineNQ;
    private final AircraftNQ aircraftNQ;
    private final AppConfig appConfig;

    @Autowired
    private BookmarkSV bookmarkSV;

    private final CurrencySV currencySV;
    private final CabinRP cabinRP;
    private final MealRP mealRP;
    private final ShoppingUtils shoppingUtils;
    private final MarkupNQ markupNQ;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * shopping transform
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingResponse
     * @return ShoppingTransformEntity
     */
    @SneakyThrows
    public ShoppingTransformEntity getShoppingTransform(ShoppingResponseEntity shoppingResponse) {

        if (!validateSabreResponse(shoppingResponse)) return null;

        var requestId = shoppingResponse.getId();
        var tripType = shoppingResponse.getQuery().getQuery().getTripType();

        var directions = shoppingResponse.getQuery()
            .getQuery()
            .getLegs()
            .stream()
            .map(flightLeg -> flightLeg.getOrigin().concat("-").concat(flightLeg.getDestination()))
            .collect(Collectors.joining("~"));

        var transform = new TransformSabreMerger(getTransformSabres(shoppingResponse.getResponses()), directions).processingTransformer();
        transform.setRequestId(requestId);
        transform.setTrip(tripType);

        var mapper = new ObjectMapper();

        log.debug("SHOPPING : [{}]", requestId);
        log.debug("SABRE RESPONSE: {}", mapper.writeValueAsString(shoppingResponse));
        log.debug("SABRE TRANSFORM RESPONSE: {}", mapper.writeValueAsString(transform));

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
     * @param currency
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetail(ShoppingTransformEntity source, long locale, String currency) {

        if (source == null) return null;

        var airlines = source.getAirlines();
        var aircrafts = source.getAircrafts();
        var locations = source.getLocations();
        var layoverAirports = source.getLayoverAirports();
        var legs = source.getLegs();

        for (Airline airline : airlines) {

            var airlineTO = airlineNQ.getAirlineInformation(airline.getCode(), locale);

            if (airlineTO != null) {

                airline.setName(airlineTO.getAirbus());
                airline.setOperatingBy(airlineTO.getAirline());
                airline.setUrl90(appConfig.getAIRLINE_LOGO_PATH() + "/90/" + airlineTO.getLogo());
                airline.setUrl45(appConfig.getAIRLINE_LOGO_PATH() + "/45/" + airlineTO.getLogo());
                airline.setCurrency(currency);

            }

        }

        for (Aircraft aircraft : aircrafts) {

            var aircraftTO = aircraftNQ.getAircraftInformation(aircraft.getCode(), 1);

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

        for (LayoverAirport layoverAirport : layoverAirports) {

            var layoverAirportTO = flightLocationNQ.getFlightLocationInformation(layoverAirport.getCode(), locale);

            if (layoverAirportTO != null) {

                layoverAirport.setAirport(layoverAirportTO.getAirport());
                layoverAirport.setCity(layoverAirportTO.getCity());
                layoverAirport.setLatitude(layoverAirportTO.getLatitude().doubleValue());
                layoverAirport.setLongitude(layoverAirportTO.getLongitude().doubleValue());
                layoverAirport.setCurrency(currency);

            }

        }

        var cabins = cabinRP.findAll();
        var meals = mealRP.findAll();

        for (Leg leg : legs) {

            for (LegSegmentDetail segment : leg.getSegments()) {

                var cabin = cabins
                    .stream()
                    .filter(cabinEntity -> cabinEntity.getCabinCode().equalsIgnoreCase(segment.getCabin()))
                    .findFirst()
                    .map(CabinEntity::getCabinName)
                    .orElse(segment.getCabin());

                var meal = meals
                    .stream()
                    .filter(mealEntity -> mealEntity.getMealCode().equalsIgnoreCase(segment.getMeal()))
                    .findFirst()
                    .map(MealEntity::getMealName)
                    .orElse(segment.getMeal());

                segment.setCabinName(cabin);
                segment.setMealName(meal);

            }

        }

        source.setAirlines(airlines);
        source.setAircrafts(aircrafts);
        source.setLocations(locations);
        source.setLayoverAirports(layoverAirports);
        source.setLegs(legs);

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
    @SneakyThrows
    public ShoppingTransformEntity getShoppingTransformDetailMarkup(ShoppingTransformEntity source, BigDecimal markup, String currency) {

        if (source == null) return null;

        var prices = source.getPrices();

        var baseCurrency = "USD";
        var fromRate = this.currencySV.getExchangeRateByCode(baseCurrency);
        var toRate = this.currencySV.getExchangeRateByCode(currency);

        var paymentMarkup = markupNQ.getGeneralMarkupPayment();

        for (PriceDetail price : prices) {

            BigDecimal totalBaseAmount = BigDecimal.ZERO;
            BigDecimal totalCurrencyExchangedAmount = BigDecimal.ZERO;

            var passengers = 0;

            for (PriceList detail : price.getDetails()) {

                log.debug("Price detail : {}", price.getId());

                var baseFareAmount = detail.getBaseFare();
                var taxAmount = detail.getTax();
                var totalAmount = NumberFormatter.trimAmount(baseFareAmount.add(taxAmount));

                var baseFareMarkupAmount = CalculatorUtils.getAmountPercentage(baseFareAmount, markup);
                var baseFarePaymentMarkupAmount = CalculatorUtils.getAmountPercentage(baseFareAmount.add(baseFareMarkupAmount), paymentMarkup.getMarkup());
                var baseFareTotalAmount = NumberFormatter.trimAmount(baseFareAmount.add(baseFareMarkupAmount).add(baseFarePaymentMarkupAmount));

                var taxMarkupAmount = CalculatorUtils.getAmountPercentage(taxAmount, markup);
                var taxPaymentMarkupAmount = CalculatorUtils.getAmountPercentage(taxAmount.add(taxMarkupAmount), paymentMarkup.getMarkup());
                var taxTotalAmount = NumberFormatter.trimAmount(taxAmount.add(taxMarkupAmount).add(taxPaymentMarkupAmount));

                var totalMarkupAmount = NumberFormatter.trimAmount(baseFareTotalAmount.add(taxTotalAmount).multiply(new BigDecimal(detail.getQuantity())));

                totalBaseAmount = totalBaseAmount.add(totalMarkupAmount);

                log.debug("SABRE: BASE_FARE({}), TAX_AMOUNT({}), TOTAL_AMOUNT({})", baseFareAmount, taxAmount, totalAmount);
                log.debug("MARKUP: USER_MARKUP({}), PAYMENT_MARKUP({}), SABRE_AMOUNT({}), TOTAL_AMOUNT({})", baseFareMarkupAmount, baseFarePaymentMarkupAmount, totalAmount, totalMarkupAmount);

                var baseFareCurrencyExchange = new ExchangeCurrencyTA(baseCurrency, fromRate, currency, toRate, baseFareTotalAmount.doubleValue());
                var taxCurrencyExchange = new ExchangeCurrencyTA(baseCurrency, fromRate, currency, toRate, taxTotalAmount.doubleValue());

                var baseFareCurrencyExchangedAmount = NumberFormatter.trimAmount(currencySV.getExchangeRateConverter(baseFareCurrencyExchange));
                var taxCurrencyExchangedAmount = NumberFormatter.trimAmount(currencySV.getExchangeRateConverter(taxCurrencyExchange));

                var totalExchangedAmount = NumberFormatter.trimAmount(baseFareCurrencyExchangedAmount.add(taxCurrencyExchangedAmount).multiply(new BigDecimal(detail.getQuantity())));

                totalCurrencyExchangedAmount = totalCurrencyExchangedAmount.add(totalExchangedAmount);

                detail.setBaseCurrency(baseCurrency);
                detail.setBaseCurrencyBaseFare(baseFareTotalAmount);
                detail.setBaseCurrencyTax(taxTotalAmount);

                detail.setCurrency(currency);
                detail.setBaseFare(baseFareCurrencyExchangedAmount);
                detail.setTax(taxCurrencyExchangedAmount);

                passengers += detail.getQuantity();
            }


            var commissionAmount = price.getTotalCommissionAmount();
            var commissionExchangedAmount = NumberFormatter.trimAmount(currencySV.getExchangeRateConverter(new ExchangeCurrencyTA(baseCurrency, fromRate, currency, toRate, commissionAmount.doubleValue())));

            var grandTotalExchanged = NumberFormatter.trimAmount(totalCurrencyExchangedAmount.subtract(commissionExchangedAmount));
            var grandTotalExchangedAverage = NumberFormatter.trimAmount(grandTotalExchanged.divide(BigDecimal.valueOf(passengers), RoundingMode.HALF_DOWN));
            var totalExchangedAverage = NumberFormatter.trimAmount(totalCurrencyExchangedAmount.divide(BigDecimal.valueOf(passengers), RoundingMode.HALF_DOWN));

            var grandTotal = NumberFormatter.trimAmount(totalBaseAmount.subtract(commissionAmount));
            var grandTotalAverage = NumberFormatter.trimAmount(grandTotal.divide(BigDecimal.valueOf(passengers), RoundingMode.HALF_DOWN));
            var totalAverage = NumberFormatter.trimAmount(totalBaseAmount.divide(BigDecimal.valueOf(passengers), RoundingMode.HALF_DOWN));

            var priceExchanged = new Price();
            priceExchanged.setCurrency(currency);
            priceExchanged.setTotal(totalCurrencyExchangedAmount);
            priceExchanged.setTotalAverage(totalExchangedAverage);
            priceExchanged.setGrandTotal(grandTotalExchanged);
            priceExchanged.setGrandTotalAverage(grandTotalExchangedAverage);
            priceExchanged.setTotalCommission(commissionExchangedAmount);

            var priceBased = new Price();
            priceBased.setCurrency(baseCurrency);
            priceBased.setTotal(totalBaseAmount);
            priceBased.setTotalAverage(totalAverage);
            priceBased.setGrandTotal(grandTotal);
            priceBased.setGrandTotalAverage(grandTotalAverage);

            price.setPrice(priceExchanged);
            price.setBasePrice(priceBased);

        }

        source.setPrices(prices);

        var mapper = new ObjectMapper();
        log.debug("SABRE TRANSFORM MARKUP RESPONSE: {}", mapper.writeValueAsString(source));

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

        List<LayoverAirport> layoverAirports = source.getLayoverAirports();

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

                            return priceDetail.getPrice().getGrandTotal().doubleValue();
                        }).mapToDouble(Double::doubleValue).sum();

                    lowestPrice = Double.parseDouble(formatter.format(lowestPrice));

                    for (Airline airline : airlines) {
                        if (airline.getCode().equalsIgnoreCase(airlineCode)) {

                            if (airline.getPrice().doubleValue() == 0) {
                                airline.setPrice(NumberFormatter.trimAmount(lowestPrice));
                            }

                            if (airline.getPrice().doubleValue() > lowestPrice) {
                                airline.setPrice(NumberFormatter.trimAmount(lowestPrice));
                            }

                            break;
                        }
                    }

                    for (Airline airline : airlines) {

                        if (airline.getPrice().doubleValue() != 0) {
                            itinerary.getLegGroups().forEach(legGroup -> {

                                if (legGroup.getAirline().equals(airline.getCode())) {

                                    legGroup.getLegsDesc().forEach(legDescription -> {
                                        var legDetail = source.getLegs().stream().filter(leg -> leg.getId().equalsIgnoreCase(legDescription.getLeg())).findFirst().get();
                                        legDetail.getSegments().forEach(segmentDetail -> {
                                            if (segmentDetail.getLayoverAirport() != null) {
                                                layoverAirports.forEach(layoverAirport -> {
                                                    if (layoverAirport.getCode().equals(segmentDetail.getLayoverAirport())) {
                                                        if (layoverAirport.getPrice().doubleValue() == 0) {
                                                            layoverAirport.setPrice(NumberFormatter.trimAmount(airline.getPrice()));
                                                        }

                                                        if (layoverAirport.getPrice().doubleValue() > airline.getPrice().doubleValue()) {
                                                            layoverAirport.setPrice(NumberFormatter.trimAmount(airline.getPrice()));
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    });

                                }

                            });
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
     * get shopping data detail with favorite filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightShoppingRQ
     * @param source
     * @param userAuthenticationMetaTA
     * @return
     */
    @Override
    public ShoppingTransformEntity getShoppingTransformDetailWithFavorite(FlightShoppingRQ flightShoppingRQ, ShoppingTransformEntity source, UserAuthenticationMetaTA userAuthenticationMetaTA) {

        if (source == null) return null;

        if (!userAuthenticationMetaTA.isAuthenticated()) return source;

        var bookmarks = bookmarkSV.get(flightShoppingRQ, userAuthenticationMetaTA);

        bookmarks
            .forEach(bookmarkAirline -> {
                if (bookmarkAirline.isDirect()) {
                    source
                        .getDirect()
                        .stream()
                        .filter(itinerary -> itinerary.getId().split("@")[2].equalsIgnoreCase(bookmarkAirline.getAirline()))
                        .findFirst()
                        .ifPresent(itinerary -> itinerary.setFavorite(true));
                } else {
                    source
                        .getCheapest()
                        .stream()
                        .filter(itinerary -> itinerary.getId().split("@")[2].equalsIgnoreCase(bookmarkAirline.getAirline()))
                        .findFirst()
                        .ifPresent(itinerary -> itinerary.setFavorite(true));
                }

            });

        var directs = source
            .getDirect()
            .stream()
            .sorted(Comparator.comparing(itinerary -> !itinerary.isFavorite()))
            .collect(Collectors.toList());

        var cheaps = source
            .getCheapest()
            .stream()
            .sorted(Comparator.comparing(itinerary -> !itinerary.isFavorite()))
            .collect(Collectors.toList());

        source.setDirect(directs);
        source.setCheapest(cheaps);


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

    @Override
    public void setShoppingDetail(String id, ShoppingTransformEntity source) {
        instance.getMap(TRANSFORM_CACHED_NAME).put(id, source, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }

    @Override
    public ShoppingTransformEntity getShoppingDetail(String id) {
        return (ShoppingTransformEntity) instance.getMap(TRANSFORM_CACHED_NAME).getOrDefault(id, null);
    }

    @Override
    public void setNewClassOfService(String id, List<String> classOfService) {
        instance.getMap(CLASS_OF_SERVICE_CACHED_NAME).put(id, classOfService, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }

    @Override
    public List<String> getNewClassOfService(String id) {
        return (List<String>) instance.getMap(CLASS_OF_SERVICE_CACHED_NAME).getOrDefault(id, new ArrayList<>());
    }

    @Override
    public void setFareBasis(String id, List<String> fareBasis) {
        instance.getMap(FARE_BASIS_CACHED_NAME).put(id, fareBasis, appConfig.getHAZELCAST_EXPIRED_TIME(), TimeUnit.SECONDS);
    }

    @Override
    public List<String> getFareBasis(String id) {
        return (List<String>) instance.getMap(FARE_BASIS_CACHED_NAME).getOrDefault(id, new ArrayList<>());
    }

}
