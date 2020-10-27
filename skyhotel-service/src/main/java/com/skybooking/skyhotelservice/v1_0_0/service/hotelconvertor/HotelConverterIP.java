package com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor;

import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.constant.PromotionTypeConstant;
import com.skybooking.skyhotelservice.constant.SubReviewConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.AvailabilityHotelRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.review.ReviewRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.CancellationPolicyRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.CancellationRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.FeeRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.PromotionRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.RateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.ContentRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.RateTaxesHBRS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.markup.HotelMarkupEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.markup.HotelMarkupRP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CancellationPolicy;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.BoardRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility.HotelFacilityRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreItemRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreRS;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS.*;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.mapper.HotelConverterMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelConverterIP implements HotelConverterSV {

    private final HotelConverterMapper mapper;
    private final ModelMapper modelMapper;
    private final HotelMarkupRP hotelMarkupRP;

    @Override
    public List<HotelRS> availabilities(AvailabilityRSDS availabilityRSDS, AvailabilityRQ availabilityRQ) {

        List<AvailabilityHotelRSDS> availabilities = availabilityRSDS.getData().getAvailabilities();
        if (availabilities.isEmpty()) return Collections.emptyList();

        Map<Integer, ContentRSDS> hotelContents = availabilityRSDS
            .getData()
            .getHotelContents()
            .parallelStream()
            .collect(Collectors.toMap(ContentRSDS::getCode, content -> content));

        ResourceRSDS resource = availabilityRSDS.getData().getResource();

        final List<HotelMarkupEntity> markup = hotelMarkupRP.getAllByType("SKYUSER");

        return availabilities
            .parallelStream()
            .map(getAvailabilityHotelResponse(availabilityRQ, hotelContents, resource, markup))
            .collect(Collectors.toList());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get availability hotel response function
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityRQ
     * @param hotelContents
     * @param resource
     * @param markup
     * @return Function
     */
    private Function<AvailabilityHotelRSDS, HotelRS> getAvailabilityHotelResponse(AvailabilityRQ availabilityRQ, Map<Integer, ContentRSDS> hotelContents, ResourceRSDS resource, List<HotelMarkupEntity> markup) {

        return availabilityHotel -> {

            ContentRSDS content = hotelContents.get(availabilityHotel.getCode());

            HotelRS hotel = new HotelRS();
            hotel.setScore(getHotelScoreResponse(availabilityHotel));

            // set hotel detail
            setHotelResponseDetail(
                availabilityHotel,
                availabilityRQ,
                content,
                resource,
                markup,
                hotel
            );

            return hotel;
        };
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set hotel response in detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityHotel
     * @param availabilityRQ
     * @param content
     * @param resource
     * @param markup
     * @param hotel
     */
    private void setHotelResponseDetail(AvailabilityHotelRSDS availabilityHotel, AvailabilityRQ availabilityRQ, ContentRSDS content, ResourceRSDS resource, List<HotelMarkupEntity> markup, HotelRS hotel) {

        if (content.getRooms().isEmpty()) return;

        final Map<String, RoomRSDS> rooms = availabilityHotel
            .getRooms()
            .parallelStream()
            .collect(Collectors.toMap(RoomRSDS::getCode, room -> room));

        hotel.setCode(content.getCode());
        hotel.setBasic(getHotelBasicResponse(content));
        hotel.setLocation(getHotelLocationResponse(content));
        hotel.setBoards(getHotelBoardResponse(content));
        hotel.setPhones(getHotelPhoneResponse(content));
        hotel.setInterestPoints(getHotelInterestPointResponse(content));
        hotel.setImages(getHotelImageResponse(content, resource.getImageUrl().getMedium()));
        hotel.setSegments(getHotelSegmentResponse(content));
        hotel.setRooms(getHotelRoomResponse(hotel, content, rooms, availabilityRQ, markup));
        hotel.setFacility(getHotelFacilityResponse(content));

        // get rate price of first room availability in hotel
        RateRS roomRate = getBestRate(hotel);

        hotel.setPriceUnit(getHotelPriceUnit(roomRate));
        hotel.setOffer(getHotelOffer(roomRate));
        hotel.setPaymentType(roomRate.getPaymentType());

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get the best rate with cheapest price and with promotion
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotel: HotelRS
     * @return RateRS
     */
    private RateRS getBestRate(HotelRS hotel) {
        List<RateRS> rates = hotel
            .getRooms()
            .stream()
            .map(RoomRS::getRates)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        Comparator<RateRS> compareByPrice = Comparator
            .comparing(rateRS -> rateRS
                .getPrice()
                .getPriceUnit()
                .getAmountAfterDiscount());

        return rates
            .stream()
            .min(compareByPrice)
            .get();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel special offer if existed
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param rateRS: RateRS
     * @return SpecialOfferRS
     */
    private SpecialOfferRS getHotelOffer(RateRS rateRS) {
        BigDecimal discountAmount = rateRS.getPrice().getPriceUnit().getDiscountAmount();
        SpecialOfferRS offer = null;
        if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            offer = new SpecialOfferRS();
            offer.setType(PromotionTypeConstant.SPECIAL);

            BigDecimal discountPercentage = rateRS.getPrice().getPriceUnit().getDiscountPercentage();
            offer.setPercentage(discountPercentage);
        }
        return offer;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel price unit
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param roomRate
     * @return PriceUnitRS
     */
    private PriceUnitRS getHotelPriceUnit(RateRS roomRate) {
        return roomRate.getPrice().getPriceUnit();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel facility response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return HotelFacilityRS
     */
    private HotelFacilityRS getHotelFacilityResponse(ContentRSDS content) {
        return mapper.toHotelFacilityRS(content.getFacility());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel room response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotel
     * @param content
     * @param rooms
     * @param availabilityRQ
     * @param markup
     * @return List
     */
    private List<RoomRS> getHotelRoomResponse(HotelRS hotel, ContentRSDS content, Map<String, RoomRSDS> rooms, AvailabilityRQ availabilityRQ, List<HotelMarkupEntity> markup) {

        return content
            .getRooms()
            .stream()
            .map(it -> {

                final List<RateRS> rates = rooms.get(it.getRoomCode())
                    .getRates()
                    .stream()
                    .map(getHotelRoomRateResponse(hotel, availabilityRQ, markup))
                    .collect(Collectors.toList());

                RoomRS roomRS = mapper.toRoomRS(it);
                roomRS.setRates(rates);

                return roomRS;

            })
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel room rate response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotel
     * @param availabilityRQ
     * @param markup
     * @return Function
     */
    private Function<RoomRateRSDS, RateRS> getHotelRoomRateResponse(HotelRS hotel, AvailabilityRQ availabilityRQ, List<HotelMarkupEntity> markup) {
        return rate -> {
            final RateRSDS rateRSDS = mapper.toRateRSDS(rate);

            // checking hotel cancellation policy of each rooms
            setHotelCancellationPolicy(hotel, rate);

            if (rate.getCancellationPolicies() != null) {
                final List<FeeRSDS> feeRSDS = rate
                    .getCancellationPolicies()
                    .stream()
                    .map(cancellation -> new FeeRSDS(
                        new BigDecimal(cancellation.getAmount()),
                        CurrencyConstant.USD.CODE, cancellation.getFrom()))
                    .collect(Collectors.toList());
                rateRSDS.setCancellation(new CancellationRSDS(
                    !rate.getCancellationPolicies().isEmpty(),
                    feeRSDS
                ));
            }

            if (rate.getPromotions() != null) {
                final List<PromotionRSDS> promotions = rate
                    .getPromotions()
                    .stream()
                    .map(mapper::toPromotionRSDS)
                    .collect(Collectors.toList());

                rateRSDS.setPromotions(promotions);
            }

            final PriceRS priceRS = calculatePrices(
                availabilityRQ.getCheckIn(),
                availabilityRQ.getCheckOut(),
                availabilityRQ.getRoom(),
                markup, rateRSDS);

            RateRS rateRS = mapper.toRateRS(rateRSDS);
            rateRS.setPrice(priceRS);

            rateRS.setRooms(availabilityRQ.getRoom());
            rateRS.setAdults(availabilityRQ.getAdult());
            rateRS.setChildren(availabilityRQ.getChildren().size());

            CancellationRS cancellation = rateRS.getCancellation();
            if (cancellation != null)
                cancellation.setDetail(getCancellationDetail(rate, false));
            rateRS.setCancellation(cancellation);

            if (rateRS
                .getPrice()
                .getPriceUnit()
                .getDiscountAmount()
                .compareTo(BigDecimal.valueOf(0)) > 0) {
                SpecialOfferRS offerRS = new SpecialOfferRS();
                offerRS.setType(PromotionTypeConstant.SPECIAL);
                offerRS.setPercentage(rateRS
                    .getPrice()
                    .getPriceUnit()
                    .getDiscountPercentage());

                rateRS.setOffer(offerRS);

            }

            return rateRS;

        };
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set hotel cancellation policy by room rate policy
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotel
     * @param rate
     */
    private void setHotelCancellationPolicy(HotelRS hotel, RoomRateRSDS rate) {

        if (hotel.getPolicy() != null && hotel.getPolicy().equals(hotel.getPolicy())) return;

        if (rate.getCancellationPolicies() != null) {
            CancellationDetailRS cancellationDetail = getCancellationDetail(rate, true);
            hotel.setCancellation(cancellationDetail);
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Cancellation Policy detail for each rate or hotel see (isHotel)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param rate
     * @param isHotel
     * @return
     */
    private CancellationDetailRS getCancellationDetail(RoomRateRSDS rate, boolean isHotel) {
        if (rate.getCancellationPolicies().isEmpty()) {
            return new CancellationDetailRS(
                CancellationTypeConstant.NON_REFUNDABLE,
                CancellationTypeConstant.NON_REFUNDABLE.getValue(),
                null);
        }
        ZonedDateTime now = ZonedDateTime.now();
        CancellationPolicyRSDS cancellationPolicyRSDS = rate.getCancellationPolicies().get(0);
        if (now.isBefore(DatetimeUtil.toZonedDateTime(cancellationPolicyRSDS.getFrom()))) {
            if (isHotel) {
                return new CancellationDetailRS(
                    CancellationTypeConstant.FREE,
                    CancellationTypeConstant.FREE.getValue(),
                    cancellationPolicyRSDS.getFrom()
                );
            }
            String str = CancellationTypeConstant.FREE_BEFORE.getValue()
                .replace("{{datetime}}",
                    Objects.requireNonNull(DatetimeUtil
                        .toFormattedDateTime(cancellationPolicyRSDS.getFrom())));
            return new CancellationDetailRS(
                CancellationTypeConstant.FREE_BEFORE,
                str,
                cancellationPolicyRSDS.getFrom()
            );
        }
        return new CancellationDetailRS(
            CancellationTypeConstant.PARTIAL,
            CancellationTypeConstant.PARTIAL.getValue(),
            null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel segment response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return List
     */
    private List<SegmentRS> getHotelSegmentResponse(ContentRSDS content) {
        return content
            .getSegments()
            .parallelStream()
            .map(mapper::toSegmentRS)
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel image response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @param imageUrl
     * @return List
     */
    private List<ImageRS> getHotelImageResponse(ContentRSDS content, String imageUrl) {
        return content
            .getImages()
            .parallelStream()
            .map(it -> {
                final ImageRS image = mapper.toImageRS(it);
                image.setThumbnailUrl(imageUrl + image.getThumbnail());
                return image;
            })
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel interest point response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return List
     */
    private List<InterestPointRS> getHotelInterestPointResponse(ContentRSDS content) {
        return content
            .getInterestPoints()
            .parallelStream()
            .map(mapper::toInterestPointRS)
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel phone response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return List
     */
    private List<PhoneRS> getHotelPhoneResponse(ContentRSDS content) {
        return content
            .getPhones()
            .parallelStream()
            .map(mapper::toPhoneRS)
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel board response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return List
     */
    private List<BoardRS> getHotelBoardResponse(ContentRSDS content) {
        return content
            .getBoards()
            .parallelStream()
            .map(mapper::toBoardRS)
            .collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel location response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return LocationRS
     */
    private LocationRS getHotelLocationResponse(ContentRSDS content) {
        return mapper.toLocationRS(content.getDestination());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel basic information response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param content
     * @return BasicRS
     */
    private BasicRS getHotelBasicResponse(ContentRSDS content) {
        return mapper.toBasicRS(content.getBasic());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel score
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityHotel
     * @return ScoreRS
     */
    private ScoreRS getHotelScoreResponse(AvailabilityHotelRSDS availabilityHotel) {
        return Optional
            .ofNullable(availabilityHotel.getReviews())
            .orElse(Collections.emptyList())
            .stream()
            .findFirst()
            .map(getReviewHotelScore()).orElse(new ScoreRS());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get review hotel score
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Function
     */
    private Function<ReviewRSDS, ScoreRS> getReviewHotelScore() {

        return review -> {

            ScoreRS scoreRS = new ScoreRS();
            scoreRS.setTitle("Excellent");
            scoreRS.setNumber(review.getRate());
            scoreRS.setReviewCount(review.getReviewCount());

            // get score items
            List<ScoreItemRS> scoreItems = SubReviewConstant
                .LIST
                .stream()
                .map(subReview -> new ScoreItemRS(subReview, 4.7f))
                .collect(Collectors.toList());

            scoreRS.setSubScore(scoreItems);

            return scoreRS;
        };
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get hotel price response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param checkIn
     * @param checkOut
     * @param numberOfRooms
     * @param hotelMarkupEntities
     * @param rate
     * @return PriceRS
     */
    private PriceRS calculatePrices(
        String checkIn, String checkOut,
        Integer numberOfRooms,
        List<HotelMarkupEntity> hotelMarkupEntities,
        RateRSDS rate) {
        // declare variable
        String currency = CurrencyConstant.USD.CODE;
        long numberOfNights = DatetimeUtil.night(checkIn, checkOut);
        RateTaxesHBRS taxesHBRS = null;
        // tax
        if (rate.getTaxes() != null) {
            taxesHBRS = mapper.toRateTaxesHBRS(rate.getTaxes());
        }
        // get exclude tax
        BigDecimal excludeTaxBeforeMarkup = CalculatePriceUtil.excludeTaxBeforeMarkup(taxesHBRS);
        // get breakdown discount
        List<Discount> discounts = CalculatePriceUtil.breakdownDiscountUnit(rate, BigDecimal.valueOf(numberOfNights));
        // get discount detail
        DiscountDetail discountDetail = CalculatePriceUtil
            .discountDetail(discounts, BigDecimal.valueOf(numberOfRooms), BigDecimal.valueOf(numberOfNights));
        // get net detail
        NetPriceDetail netDetail = CalculatePriceUtil
            .detailNet(rate.getNet(), excludeTaxBeforeMarkup, BigDecimal.valueOf(numberOfRooms), BigDecimal.valueOf(numberOfNights));
        // get markup
        MarkupPriceDetail markup = CalculatePriceUtil
            .markupPricesDetail(hotelMarkupEntities, netDetail.getUnitNet(), BigDecimal.valueOf(numberOfRooms), BigDecimal.valueOf(numberOfNights));

        List<CancellationPolicy> cancellationPolicy = new ArrayList();
        // get cancellation policy
        if (rate.getCancellation() != null && rate.getCancellation().getCancellable()) {
            cancellationPolicy = CalculatePriceUtil
                .breakdownCancellationPolicy(rate.getCancellation().getFees(), markup.getMarkupPercentage(), BigDecimal.valueOf(numberOfNights));
        }
        // get tax breakdown unit
        List<Tax> breakdownTaxUnit = CalculatePriceUtil
            .breakdownTaxUnit(taxesHBRS, markup, rate.getRooms(), numberOfNights);
        // get total tax detail
        TaxDetail taxDetail = CalculatePriceUtil.taxDetail(breakdownTaxUnit, numberOfRooms, numberOfNights);
        // get unit amount
        BigDecimal unitAmount = CalculatePriceUtil
            .unitAmount(netDetail.getUnitNet(), markup.getMarkupAmount(), taxDetail.getUnitTax(), discountDetail.getUnitDiscount());
        // total discount
        BigDecimal unitDiscountPercentage = CalculatePriceUtil.calculateDiscountPercentage(unitAmount, discountDetail.getUnitDiscount());
        // get total price
        TotalPriceDetail totalPrice = CalculatePriceUtil
            .detailTotalPrice(unitAmount, discountDetail.getDiscount(), taxDetail.getTax(), BigDecimal.valueOf(numberOfRooms), BigDecimal.valueOf(numberOfNights));
        // get amount after discount
        BigDecimal amountAfterDiscount = unitAmount.subtract(discountDetail.getUnitDiscount());
        // set value to response
        PriceUnitRS priceUnitRS = new PriceUnitRS();

        priceUnitRS.setNetAmount(NumberFormatter.trimAmount(netDetail.getUnitNet()));
        priceUnitRS.setAmount(NumberFormatter.trimAmount(unitAmount));
        priceUnitRS.setTaxAmount(NumberFormatter.trimAmount(taxDetail.getUnitTax()));
        priceUnitRS.setAmountAfterDiscount(NumberFormatter.trimAmount(amountAfterDiscount));
        priceUnitRS.setDiscountPercentage(NumberFormatter.trimPercentage(unitDiscountPercentage));
        priceUnitRS.setDiscountAmount(NumberFormatter.trimAmount(discountDetail.getUnitDiscount()));
        priceUnitRS.setMarkupAmount(NumberFormatter.trimAmount(markup.getMarkupAmount()));
        priceUnitRS.setCurrency(currency);
        priceUnitRS.setTaxes(breakdownTaxUnit);
        priceUnitRS.setDiscounts(discounts);
        priceUnitRS.setCancellations(cancellationPolicy);

        DetailRS detailRS = new DetailRS();

        detailRS.setMarkupAmount(NumberFormatter.trimAmount(markup.getTotalMarkupAmount()));
        detailRS.setTotalNight(numberOfNights);
        detailRS.setMarkupPercentage(markup.getMarkupPercentage());
        detailRS.setSubTotalAmount(NumberFormatter.trimAmount(totalPrice.getAmountIncludeDiscount()));
        detailRS.setTotalNet(NumberFormatter.trimAmount(netDetail.getTotalNet()));
        detailRS.setTotalAmount(NumberFormatter.trimAmount(totalPrice.getAmountAfterDiscount()));
        detailRS.setTotalTaxesAmount(NumberFormatter.trimAmount(taxDetail.getTax()));
        detailRS.setTaxes(taxDetail.getTaxes());
        detailRS.setDiscountAmount(discountDetail.getDiscount());
        detailRS.setDiscountPercentage(NumberFormatter.trimPercentage(unitDiscountPercentage));
        detailRS.setDiscounts(discountDetail.getDiscounts());

        detailRS.setCurrency(currency);

        PriceRS priceRS = new PriceRS();
        priceRS.setPriceUnit(priceUnitRS);
        priceRS.setDetail(detailRS);

        return priceRS;
    }

}
