package com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.constant.SubReviewConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.AvailabilityHotelRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.review.ReviewRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.*;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.ContentRSDS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.BoardRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility.HotelFacilityRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.price.PriceUnitRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreItemRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreRS;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatorCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HotelConverterIP implements HotelConverterSV {

    @Autowired
    private CalculatorCM calculatorCM;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HotelRS> availabilities(AvailabilityRSDS availabilityRSDS) {

        List<HotelRS> response = new ArrayList<>();
        var data = availabilityRSDS.getData();
        Map<Integer, ContentRSDS> contentRSDSMap = new HashMap<>();

        data.getHotelContents()
            .forEach(contentRSDS ->
                contentRSDSMap.put(contentRSDS.getCode(), contentRSDS));

        data.getAvailabilities()
            .forEach(availabilityHotelRSDS -> {

                HotelRS hotelRS = new HotelRS();

                if (availabilityHotelRSDS.getReviews().size() > 0) {
                    ReviewRSDS review = availabilityHotelRSDS.getReviews().get(0);
                    List<ScoreItemRS> scoreItems = new ArrayList<>();
                    ScoreRS scoreRS = new ScoreRS();

                    scoreRS.setTitle("Excellent");
                    scoreRS.setNumber(review.getRate());
                    scoreRS.setReviewCount(review.getReviewCount());

                    SubReviewConstant.LIST.forEach(s -> {
                        ScoreItemRS scoreItemRS = new ScoreItemRS(s, (float) 4.7);
                        scoreItems.add(scoreItemRS);
                    });

                    scoreRS.setSubScore(scoreItems);
                    hotelRS.setScore(scoreRS);
                }

                RoomRSDS room = availabilityHotelRSDS.getRooms().get(0);
                RoomRateRSDS roomRate = room.getRates().get(0);

                BigDecimal amount = calculatorCM.markupPrice(new BigDecimal(roomRate.getNet()));

                PriceUnitRS priceUnitRS = new PriceUnitRS();
                priceUnitRS.setAmount(amount);
                priceUnitRS.setAmountAfterDiscount(amount);
                hotelRS.setPriceUnit(priceUnitRS);

                hotelRS.setPaymentType(RateRS.PaymentType.getByValue(roomRate.getPaymentType()));
                setContentDataToList(contentRSDSMap
                    .get(availabilityHotelRSDS.getCode()), availabilityHotelRSDS, data.getResource(), hotelRS);

                response.add(hotelRS);

            });

        return response;

    }

    private void setContentDataToList(ContentRSDS contentRSDS,
                                      AvailabilityHotelRSDS availabilityHotelRSDS,
                                      ResourceRSDS resourceRSDS,
                                      HotelRS hotelRS) {

        Map<String, RoomRSDS> roomRSDSMap = availabilityHotelRSDS
            .getRooms()
            .stream()
            .collect(Collectors.toMap(RoomRSDS::getCode, o -> o));

        hotelRS.setCode(contentRSDS.getCode());
        hotelRS.setBasic(modelMapper.map(contentRSDS.getBasic(), BasicRS.class));
        hotelRS.setLocation(modelMapper.map(contentRSDS.getDestination(), LocationRS.class));

        hotelRS.setBoards(contentRSDS
            .getBoards()
            .stream()
            .map(boardRSDS -> modelMapper.map(boardRSDS, BoardRS.class))
            .collect(Collectors.toList()));

        hotelRS.setPhones(contentRSDS
            .getPhones()
            .stream()
            .map(item -> modelMapper.map(item, PhoneRS.class))
            .collect(Collectors.toList()));

        hotelRS.setInterestPoints(contentRSDS
            .getInterestPoints()
            .stream()
            .map(item -> modelMapper.map(item, InterestPointRS.class))
            .collect(Collectors.toList()));

        hotelRS.setImages(contentRSDS
            .getImages()
            .stream()
            .map(item -> {
                ImageRS imageRS = modelMapper.map(item, ImageRS.class);
                imageRS.setThumbnailUrl(resourceRSDS.getImageUrl().getMedium() + imageRS.getThumbnail());
                return imageRS;
            })
            .collect(Collectors.toList()));

        hotelRS.setSegments(contentRSDS
            .getSegments()
            .stream()
            .map(segmentRSDS -> modelMapper.map(segmentRSDS, SegmentRS.class))
            .collect(Collectors.toList()));

        hotelRS.setRooms(contentRSDS
            .getRooms()
            .stream()
            .map(item -> {
                RoomRSDS roomRSDS = roomRSDSMap.get(item.getRoomCode());

                List<RateRS> rateRSs = roomRSDS.getRates()
                    .stream()
                    .map(rate -> {
                        RateRSDS rateRSDS1 = modelMapper.map(rate, RateRSDS.class);

                        if (rate.getCancellationPolicies() != null)
                            rateRSDS1.setCancellation(new CancellationRSDS(
                                rate.getCancellationPolicies().size() > 0,
                                rate.getCancellationPolicies()
                                    .stream()
                                    .map(c -> new FeeRSDS(
                                        new BigDecimal(c.getAmount()),
                                        CurrencyConstant.USD.CODE,
                                        c.getFrom()
                                    ))
                                    .collect(Collectors.toList())
                            ));

                        BigDecimal amount = calculatorCM.markupPrice(new BigDecimal(rate.getNet()));

                        PriceUnitRSDS priceUnitRSDS = new PriceUnitRSDS();
                        priceUnitRSDS.setAmount(amount);

                        DetailRSDS detailRSDS = new DetailRSDS();
                        detailRSDS.setNetAmount(new BigDecimal(rate.getNet()));
                        detailRSDS.setTotalAmount(amount);
                        detailRSDS.setTotalTaxesAmount(amount);

                        rateRSDS1.setPrice(new PriceRSDS(priceUnitRSDS, detailRSDS));

                        RateRS rateRS = modelMapper.map(rateRSDS1, RateRS.class);

                        rateRS.getPrice().getPriceUnit().setAmountAfterDiscount(amount);
                        rateRS.getPrice().getPriceUnit().setCurrency(CurrencyConstant.USD.CODE);
                        rateRS.getPrice().getDetail().setCurrency(CurrencyConstant.USD.CODE);

                        rateRS.setPaymentType(RateRS.PaymentType.getByValue(rateRSDS1.getPaymentType()));

                        return rateRS;
                    })
                    .collect(Collectors.toList());

                RoomRS roomRS = modelMapper.map(item, RoomRS.class);
                roomRS.setRates(rateRSs);

                return roomRS;
            })
            .collect(Collectors.toList()));

        hotelRS.setFacility(modelMapper.map(contentRSDS.getFacility(), HotelFacilityRS.class));
    }

}
