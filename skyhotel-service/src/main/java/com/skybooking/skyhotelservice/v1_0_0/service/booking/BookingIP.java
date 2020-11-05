package com.skybooking.skyhotelservice.v1_0_0.service.booking;

import com.skybooking.skyhotelservice.constant.BookingConstantPayment;
import com.skybooking.skyhotelservice.constant.BookingStatus;
import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.constant.RateType;
import com.skybooking.skyhotelservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhotelservice.exception.httpstatus.NotFoundException;
import com.skybooking.skyhotelservice.v1_0_0.client.action.booking.BookingAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking.*;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.FeeRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.RateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.BookingConfirmationHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.CheckRateHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.CheckRateHotelHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.ReserveDSRS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingRateEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached.CheckRateCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.markup.HotelMarkupEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelBookingRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelRatebreakdownRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.markup.HotelMarkupRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.bookingCached.BookingCachedSV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelCached.HotelCachedSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.CheckRateRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.CheckRateRoomRateRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.ReserveRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.PriceUnitRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RateRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RoomRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import com.skybooking.skyhotelservice.v1_0_0.util.GeneratorCM;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS.*;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookingIP extends BaseServiceIP implements BookingSV {

    private final ModelMapper modelMapper;
    private final BookingAction bookingAction;
    private final HotelBookingRP bookingRP;
    private final HotelRatebreakdownRP hoteRate;
    private final HotelCachedSV hotelCachedSV;
    private final BookingCachedSV bookingCachedSV;
    private final HotelBookingRP hotelBookingRP;
    private final JwtUtils jwtUtils;
    private final HeaderCM headerCM;

    @Autowired
    private HotelMarkupRP hotelMarkupRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function use for check rate
     * -----------------------------------------------------------------------------------------------------------------
     * @param checkRateRQ
     * @return checkRateRS
     */
    @Override
    public StructureRS checkRate(CheckRateRQ checkRateRQ) {

        CheckRateHBRQ checkRateHBRQ = modelMapper.map(checkRateRQ, CheckRateHBRQ.class);

        Optional<HotelRS> hotelRoomRates = getHotelRoomRatesFromCache(checkRateRQ, checkRateHBRQ.getRooms());
        if (hotelRoomRates.isEmpty())
            throw new NotFoundException("You are requested for expired request! Please, Go to search availability again...", null);

        List<BookingRoomHBRQ> list = checkRateHBRQ
            .getRooms()
            .stream()
            .filter(bookingRoomHBRQ -> bookingRoomHBRQ.getRateType() == RateType.RECHECK)
            .collect(Collectors.toList());

        CheckRateHBRS checkRateHBRS = null;
        if (!list.isEmpty()) {
            Mono<CheckRateHBRS> mono = bookingAction.checkRate(checkRateHBRQ);
            checkRateHBRS = mono.block();
        }

        CheckRateRS checkRateRS = new CheckRateRS();
        if (checkRateHBRS != null) {
            checkRateRS.setSummary(getCheckRateWithResponse(checkRateHBRS.getHotel(), hotelRoomRates.get(), checkRateRQ));
        } else {
            checkRateRS.setSummary(getCheckRateWithCached(hotelRoomRates.get(), checkRateRQ));
        }
        checkRateRS.setHotel(hotelRoomRates.get());

        return responseBodyWithSuccessMessage(checkRateRS);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function use for reserve
     * -----------------------------------------------------------------------------------------------------------------
     * @param reserveRQ
     * @return checkRateRS
     */
    @Override
    public StructureRS reserve(ReserveRQ reserveRQ) {

        CheckRateRQ checkRateRQ = modelMapper.map(reserveRQ, CheckRateRQ.class);

        StructureRS structureRS = this.checkRate(checkRateRQ);
        CheckRateRS checkRateRS = modelMapper.map(structureRS.getData(), CheckRateRS.class);
        HotelRS hotelRoomRates = checkRateRS.getHotel();

        Map<String, CheckRateRoomRateRQ> rqMap =
            reserveRQ.getRooms()
                .stream()
                .collect(Collectors
                    .toMap(CheckRateRoomRateRQ::getRateKey, o -> o));

        List<ReserveRoomDSRQ> reserveRoomDSRQList = hotelRoomRates
            .getRooms()
            .stream()
            .map(roomRS -> {
                ReserveRoomDSRQ reserveRoomDSRQ = new ReserveRoomDSRQ();
                reserveRoomDSRQ.setTypeCode(roomRS.getRoomCode());
                reserveRoomDSRQ.setRates(
                    roomRS.getRates()
                        .stream()
                        .map(rateRS -> {
                            ReserveRateDSRQ reserveRateDSRQ = new ReserveRateDSRQ();
                            reserveRateDSRQ.setKey(rateRS.getRateKey());
                            reserveRateDSRQ.setBoardCode(rateRS.getBoardCode());
                            reserveRateDSRQ.setClassCode(rateRS.getRateClass());
                            reserveRateDSRQ.setPaxes(
                                rqMap.get(rateRS.getRateKey())
                                    .getPaxes()
                                    .stream()
                                    .map(bookingPaxRQ -> modelMapper.map(bookingPaxRQ, BookingPaxDSRQ.class))
                                    .collect(Collectors.toList())
                            );
                            return reserveRateDSRQ;
                        })
                        .collect(Collectors.toList())
                );
                return reserveRoomDSRQ;
            })
            .collect(Collectors.toList());

        ReserveHotelDSRQ reserveHotelDSRQ = new ReserveHotelDSRQ();
        reserveHotelDSRQ.setCheckIn(reserveRQ.getCheckIn());
        reserveHotelDSRQ.setCheckOut(reserveRQ.getCheckOut());
        reserveHotelDSRQ.setCode(hotelRoomRates.getCode());
        reserveHotelDSRQ.setHolder(modelMapper.map(reserveRQ.getHolder(), HolderDSRQ.class));
        reserveHotelDSRQ.setRooms(reserveRoomDSRQList);

        ReserveDSRQ reserveDSRQ = new ReserveDSRQ();
        reserveDSRQ.setHotel(reserveHotelDSRQ);

        StructureRSDS structureRSDS;
        try {
            Mono<StructureRSDS> reserve = bookingAction.reserve(reserveDSRQ);
            structureRSDS = reserve.block();
        } catch (WebClientResponseException exception) {
            if (exception instanceof WebClientResponseException.BadRequest)
                throw new BadRequestException("Bad Request. Please check check-in and check-out date", null);
            throw exception;
        }

        ReserveDSRS data = new ReserveDSRS();
        if (structureRSDS != null) {
            modelMapper.map(structureRSDS.getData(), data);
        }

        checkRateRQ.setBookingReference(data.getCode());
        checkRateRS.setCode(data.getCode());
        checkRateRS.setHolder(reserveDSRQ.getHotel().getHolder());
        this.storeHotelBooking(checkRateRS);

        CheckRateCached checkRateCached = modelMapper.map(reserveRQ, CheckRateCached.class);
        bookingCachedSV.save(checkRateRS.getCode(), checkRateCached);

        return responseBodyWithSuccessMessage(checkRateRS);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function use for store check rate with response
     * -----------------------------------------------------------------------------------------------------------------
     * @param checkRateHotelHBRS
     * @param hotelRS
     * @param checkRateRQ
     * @return checkRateHotelRS
     */
    private CheckRateHotel getCheckRateWithResponse(CheckRateHotelHBRS checkRateHotelHBRS, HotelRS hotelRS, CheckRateRQ checkRateRQ) {
        CheckRateHotel checkRateHotelRS = new CheckRateHotel();
        // get total night from checkRateRQ
        long numberOfNights = DatetimeUtil.night(checkRateHotelHBRS.getCheckIn(), checkRateHotelHBRS.getCheckOut());
        // get list of markup price rule
        List<HotelMarkupEntity> hotelMarkupPrice = hotelMarkupRP.getAllByType("SKYUSER");
        // map room with request
        Map<String, CheckRateRoomRateRQ> roomMap = checkRateRQ
            .getRooms()
            .stream()
            .collect(Collectors.toMap(
                CheckRateRoomRateRQ::getRateKey,
                checkRateRoomRateRQ -> checkRateRoomRateRQ
            ));

        // declare variable
        List<Rate> rates = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<RateRS> ratesCached = new ArrayList<>();

        // get room form cache
        hotelRS.getRooms().forEach(roomRS -> ratesCached.addAll(roomRS.getRates()));
        Map<String, RateRS> ratesCachedMap = ratesCached
            .stream()
            .collect(Collectors.toMap(RateRS::getRateKey, o -> o));

        // loop room
        checkRateHotelHBRS.getRooms().forEach(data -> {
            Room room = new Room();
            // loop rate
            data.getRates().forEach(dataRate -> {
                Rate rate = new Rate();
                // get object from checkRateRoomRateRQ
                CheckRateRoomRateRQ checkRateRoomRateRQ = roomMap.get(dataRate.getRateKey());
                // get exclude tax before markup
                BigDecimal exTaxBeforeMarkup = CalculatePriceUtil.excludeTaxBeforeMarkup(dataRate.getTaxes());
                // get netDetail before markup
                NetPriceDetail netDetail = CalculatePriceUtil
                    .detailNet(dataRate.getNet(), exTaxBeforeMarkup, BigDecimal.valueOf(checkRateRoomRateRQ.getRooms()), BigDecimal.valueOf(numberOfNights));
                // get markup value
                MarkupPriceDetail markup = CalculatePriceUtil
                    .markupPricesDetail(hotelMarkupPrice, netDetail.getUnitNet(), BigDecimal.valueOf(checkRateRoomRateRQ.getRooms()), BigDecimal.valueOf(numberOfNights));
                // get tax breakdown unit
                List<Tax> breakdownTaxUnit = CalculatePriceUtil
                    .breakdownTaxUnit(dataRate.getTaxes(), markup, dataRate.getRooms(), numberOfNights);
                // get total tax detail
                TaxDetail taxDetail = CalculatePriceUtil.taxDetail(breakdownTaxUnit, checkRateRoomRateRQ.getRooms(), numberOfNights);
                // get discount
                RateRSDS rateRSDS = null;
                rateRSDS = modelMapper.map(dataRate, RateRSDS.class);
                List<Discount> discounts = CalculatePriceUtil
                    .breakdownDiscountUnit(rateRSDS, BigDecimal.valueOf(numberOfNights));

                DiscountDetail discountDetail = CalculatePriceUtil
                    .discountDetail(discounts, BigDecimal.valueOf(checkRateRoomRateRQ.getRooms()), BigDecimal.valueOf(numberOfNights));
                // get policy
                List<CancellationPolicy> cancellationPolicies = new ArrayList<>();
                List<FeeRSDS> feeRSDS = new ArrayList<>();
                // get cancellation policy
                if (dataRate.getCancellationPolicies() != null && !dataRate.getCancellationPolicies().isEmpty()) {
                    feeRSDS = dataRate.getCancellationPolicies().stream().map(cancel -> modelMapper.map(cancel, FeeRSDS.class)).collect(Collectors.toList());
                    cancellationPolicies = CalculatePriceUtil
                        .breakdownCancellationPolicy(feeRSDS, markup.getMarkupPercentage(), BigDecimal.valueOf(numberOfNights));
                }
                CancellationPolicyDetail cancellationPolicyDetail = CalculatePriceUtil
                    .cancellationPolicyDetail(cancellationPolicies, BigDecimal.valueOf(checkRateRoomRateRQ.getRooms()), BigDecimal.valueOf(numberOfNights));
                // get unit amount
                BigDecimal unitAmount = CalculatePriceUtil
                    .unitAmount(netDetail.getUnitNet(), markup.getMarkupAmount(), taxDetail.getUnitTax(), discountDetail.getUnitDiscount());
                // get total price => subTotalAmount and TotalAmount
                TotalPriceDetail totalPrice = CalculatePriceUtil
                    .detailTotalPrice(unitAmount, discountDetail.getDiscount(), taxDetail.getTax(), BigDecimal.valueOf(checkRateRoomRateRQ.getRooms()), BigDecimal.valueOf(numberOfNights));
                BigDecimal subTotalAmount = totalPrice.getAmountIncludeDiscount();
                BigDecimal totalAmount = totalPrice.getAmountAfterDiscount();

                // set value to rateRS
                rate.setKey(dataRate.getRateKey());
                rate.setCurrencyCode(CurrencyConstant.USD.CODE);
                rate.setUnitNet(NumberFormatter.trimAmount(netDetail.getUnitNet()));
                rate.setTotalNet(NumberFormatter.trimAmount(netDetail.getTotalNet()));
                rate.setUnitAmount(NumberFormatter.trimAmount(unitAmount));
                rate.setMarkupAmount(NumberFormatter.trimAmount(markup.getTotalMarkupAmount()));
                rate.setMarkupPercentage(markup.getMarkupPercentage());
                rate.setSubTotalAmount(NumberFormatter.trimAmount(subTotalAmount));
                rate.setTotalAmount(NumberFormatter.trimAmount(totalAmount));
                rate.setTotalDiscount(NumberFormatter.trimAmount(discountDetail.getDiscount()));
                rate.setDiscounts(discounts);
                rate.setBoardCode(dataRate.getBoardCode());
                rate.setTax(taxDetail.getTaxes());
                rate.setUnitTax(NumberFormatter.trimAmount(taxDetail.getUnitTax()));
                rate.setTotalTax(NumberFormatter.trimAmount(taxDetail.getTax()));
                rate.setTotalRoom(checkRateRoomRateRQ.getRooms());
                rate.setTotalNight(Math.toIntExact(numberOfNights));
                rate.setCommissionAmount(NumberFormatter.trimAmount(dataRate.getCommission()));
                rate.setCancellationPolicy(cancellationPolicyDetail.getCancellationPolicies());
                // get previous amount from cache
                BigDecimal previousAmount = ratesCachedMap.get(rate.getKey()).getPrice().getDetail().getTotalAmount();
                rate.setPreviousTotalAmount(NumberFormatter.trimAmount(previousAmount));
                // check isChanged
                boolean isChanged = !previousAmount.equals(rate.getTotalAmount());
                rate.setIsChanged(isChanged);

                rates.add(rate);

                // set value to roomRS
                room.setCost(NumberFormatter.trimAmount(room.getCost().add(rate.getTotalNet())));
                room.setTotalAmount(NumberFormatter.trimAmount(room.getTotalAmount().add(rate.getTotalAmount())));
                room.setMarkupAmount(NumberFormatter.trimAmount(room.getMarkupAmount().add(rate.getMarkupAmount())));
                room.setTotalTaxFees(NumberFormatter.trimAmount(room.getTotalTaxFees().add(rate.getTotalTax())));
                room.setTotalDiscountAmount(NumberFormatter.trimAmount(room.getTotalDiscountAmount().add(rate.getTotalDiscount())));
                room.setPreviousTotalAmount(NumberFormatter.trimAmount(room.getPreviousTotalAmount().add(rate.getPreviousTotalAmount())));
                room.setIsChanged(rate.getIsChanged());
                room.setCommissionAmount(NumberFormatter.trimAmount(room.getCommissionAmount().add(rate.getCommissionAmount())));

            });

            room.setCode(data.getCode());
            room.setCurrencyCode(CurrencyConstant.USD.CODE);
            room.setRates(rates);
            rooms.add(room);

            // set value to hotelRS
            checkRateHotelRS.setCost(NumberFormatter.trimAmount(checkRateHotelRS.getCost().add(room.getCost())));
            checkRateHotelRS.setTotalAmount(NumberFormatter.trimAmount(checkRateHotelRS.getTotalAmount().add(room.getTotalAmount())));
            checkRateHotelRS.setMarkupAmount(NumberFormatter.trimAmount(checkRateHotelRS.getMarkupAmount().add(room.getMarkupAmount())));
            checkRateHotelRS.setPreviousTotalAmount(NumberFormatter.trimAmount(checkRateHotelRS.getPreviousTotalAmount().add(room.getPreviousTotalAmount())));
            checkRateHotelRS.setIsChanged(room.getIsChanged());
        });

        checkRateHotelRS.setCheckIn(checkRateHotelHBRS.getCheckIn());
        checkRateHotelRS.setCheckOut(checkRateHotelHBRS.getCheckOut());
        checkRateHotelRS.setCode(checkRateHotelHBRS.getCode());
        checkRateHotelRS.setMarkupPaymentAmount(NumberFormatter.trimAmount(CalculatePriceUtil.markupPayment(checkRateHotelRS.getTotalAmount()).getMarkupAmount()));
        checkRateHotelRS.setMarkupPaymentPercentage(CalculatePriceUtil.markupPayment(checkRateHotelRS.getTotalAmount()).getMarkupPercentage());
        checkRateHotelRS.setCurrencyCode(CurrencyConstant.USD.CODE);
        checkRateHotelRS.setRooms(rooms);

        return checkRateHotelRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function use for store check rate with cached
     * -----------------------------------------------------------------------------------------------------------------
     * @param hotelRS
     * @param checkRateRQ
     * @return checkRateHotelRS
     */
    public CheckRateHotel getCheckRateWithCached(HotelRS hotelRS, CheckRateRQ checkRateRQ) {

        //TODO: Mark up price, store and create response here
        CheckRateHotel checkRateHotelRS = new CheckRateHotel();
        checkRateHotelRS.setCode(hotelRS.getCode());
        checkRateHotelRS.setCheckIn(checkRateRQ.getCheckIn());
        checkRateHotelRS.setCheckOut(checkRateRQ.getCheckOut());
        // get list room
        List<Room> rooms = getRoomCheckRateWithCached(hotelRS.getRooms(), checkRateRQ);
        // loop room for map
        rooms.forEach(room -> {
            checkRateHotelRS.setCurrencyCode(room.getCurrencyCode());

            checkRateHotelRS
                .setMarkupAmount(checkRateHotelRS
                    .getMarkupAmount()
                    .add(room.getMarkupAmount()));
            checkRateHotelRS
                .setTotalAmount(checkRateHotelRS
                    .getTotalAmount()
                    .add(room.getTotalAmount()));

            checkRateHotelRS
                .setCost(checkRateHotelRS
                    .getCost()
                    .add(room.getCost()));

            // Previously
            checkRateHotelRS
                .setPreviousTotalAmount(checkRateHotelRS
                    .getPreviousTotalAmount()
                    .add(room.getPreviousTotalAmount()));
            checkRateHotelRS
                .setIsChanged(checkRateHotelRS
                    .getIsChanged() || room.getIsChanged());
        });

        // general mark up (payment)
        checkRateHotelRS
            .setMarkupPaymentPercentage(CalculatePriceUtil
                .markupPayment(checkRateHotelRS.getTotalAmount())
                .getMarkupPercentage());

        checkRateHotelRS.setMarkupPaymentAmount(CalculatePriceUtil
            .markupPayment(checkRateHotelRS.getTotalAmount()).getMarkupAmount());
        checkRateHotelRS.setRooms(rooms);

        return checkRateHotelRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function get room check rate with cached
     * -----------------------------------------------------------------------------------------------------------------
     * @param roomRSList
     * @param checkRateRQ
     * @return rooms
     */
    private List<Room> getRoomCheckRateWithCached(List<RoomRS> roomRSList, CheckRateRQ checkRateRQ) {
        // get total nights
        long numberOfNights = DatetimeUtil.night(checkRateRQ.getCheckIn(), checkRateRQ.getCheckOut());

        Map<String, CheckRateRoomRateRQ> roomMap = checkRateRQ
            .getRooms()
            .stream()
            .collect(Collectors.toMap(
                CheckRateRoomRateRQ::getRateKey,
                checkRateRoomRateRQ -> checkRateRoomRateRQ
            ));

        List<Room> rooms = new ArrayList<>();
        // loop room
        roomRSList.forEach(roomRS -> {
            Room room = new Room();
            room.setCode(roomRS.getRoomCode());
            List<Rate> rates = new ArrayList<>();
            // loop rate
            roomRS.getRates().forEach(rateRS -> {
                Rate rate = new Rate();
                // total room come form request
                Integer rRoom = roomMap.get(rateRS.getRateKey()).getRooms();
                PriceUnitRS priceUnit = rateRS.getPrice().getPriceUnit();
                // Rate info
                rate.setKey(rateRS.getRateKey());
                rate.setCurrencyCode(priceUnit.getCurrency());
                rate.setUnitAmount(priceUnit.getAmount());
                // get total discount detail
                DiscountDetail discountDetail = CalculatePriceUtil
                    .discountDetail(priceUnit.getDiscounts(), BigDecimal.valueOf(rRoom), BigDecimal.valueOf(numberOfNights));
                // get total tax detail
                TaxDetail taxDetail = CalculatePriceUtil.taxDetail(priceUnit.getTaxes(), rRoom, numberOfNights);
                // get total price
                TotalPriceDetail totalPrice = CalculatePriceUtil
                    .detailTotalPrice(priceUnit.getAmount(), discountDetail.getDiscount(), taxDetail.getTax(), BigDecimal.valueOf(rRoom), BigDecimal.valueOf(numberOfNights));
                BigDecimal subTotalAmount = totalPrice.getAmountIncludeDiscount();
                BigDecimal totalAmount = totalPrice.getAmountAfterDiscount();
                // get cancellation policy
                CancellationPolicyDetail cancellationPolicyDetail = CalculatePriceUtil
                    .cancellationPolicyDetail(priceUnit.getCancellations(), BigDecimal.valueOf(rRoom), BigDecimal.valueOf(numberOfNights));
                // get total net
                BigDecimal totalNet = priceUnit.getNetAmount().multiply(BigDecimal.valueOf(rRoom)).multiply(BigDecimal.valueOf(numberOfNights));

                // Rate info
                rate.setBoardCode(rateRS.getBoardCode());
                rate.setUnitNet(priceUnit.getNetAmount());
                rate.setTotalNet(totalNet);
                rate.setSubTotalAmount(subTotalAmount);
                rate.setTotalAmount(totalAmount);
                rate.setTotalRoom(roomMap.get(rateRS.getRateKey()).getRooms());
                rate.setTotalNight((int) numberOfNights);
                // set mark up amount
                BigDecimal markupAmount = priceUnit.getMarkupAmount()
                    .multiply(BigDecimal.valueOf(rRoom))
                    .multiply(BigDecimal.valueOf(numberOfNights));

                rate.setMarkupPercentage(rateRS.getPrice().getDetail().getMarkupPercentage());
                rate.setMarkupAmount(markupAmount);
                rate.setPreviousTotalAmount(totalAmount);
                rate.setIsChanged(false);
                rate.setTotalTax(taxDetail.getTax());
                rate.setUnitTax(priceUnit.getTaxAmount());
                rate.setTax(taxDetail.getTaxes());
                rate.setCancellationPolicy(cancellationPolicyDetail.getCancellationPolicies());
                rate.setTotalDiscount(discountDetail.getDiscount());
                rate.setDiscounts(discountDetail.getDiscounts());

                // Room info
                room.setCost(room.getCost().add(rate.getTotalNet()));
                room.setTotalAmount(room.getTotalAmount().add(rate.getTotalAmount()));
                room.setCurrencyCode(rate.getCurrencyCode());
                room.setMarkupAmount(room.getMarkupAmount().add(rate.getMarkupAmount()));
                room.setTotalTaxFees(room.getTotalTaxFees().add(rate.getTotalTax()));
                room.setPreviousTotalAmount(room.getPreviousTotalAmount().add(rate.getTotalAmount()));
                room.setIsChanged(room.getIsChanged() || rate.getIsChanged());

                rates.add(rate);

            });
            room.setRates(rates);

            rooms.add(room);
        });

        return rooms;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function get hotel room rate with cached
     * -----------------------------------------------------------------------------------------------------------------
     * @param checkRateRQ
     * @param rates
     * @return hotelRS
     */
    private Optional<HotelRS> getHotelRoomRatesFromCache(CheckRateRQ checkRateRQ, List<BookingRoomHBRQ> rates) {
        Optional<HotelRS> hotelRS = hotelCachedSV.retrieveHotel(checkRateRQ.getRequestId(), checkRateRQ.getHotelCode());
        rates.removeAll(List.copyOf(rates));

        List<String> listRates = checkRateRQ
            .getRooms()
            .stream()
            .map(CheckRateRoomRateRQ::getRateKey)
            .distinct()
            .collect(Collectors.toList());

        hotelRS.ifPresent(hotel -> {
            List<RoomRS> rooms = hotel
                .getRooms()
                .stream()
                .peek(roomRS -> roomRS
                    .setRates(roomRS
                        .getRates()
                        .stream()
                        .filter(rateRS -> {
                            boolean isIn = listRates.contains(rateRS.getRateKey());
                            if (isIn)
                                rates.add(new BookingRoomHBRQ(
                                    rateRS.getRateKey(),
                                    RateType.getByName(rateRS.getRateType())
                                ));
                            return isIn;
                        })
                        .collect(Collectors.toList())))
                .filter(roomRS -> !roomRS.getRates().isEmpty())
                .collect(Collectors.toList());

            hotel.setRooms(rooms);
        });

        return hotelRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This function for store data booking hotel
     * -----------------------------------------------------------------------------------------------------------------
     * @param checkRateRS
     */
    @Transactional(rollbackFor = {Exception.class})
    public void storeHotelBooking(CheckRateRS checkRateRS) {
        // TODO: Store hotel booking histories
        var booking = hotelBookingRP.findFirstByOrderByIdDesc();

        HotelBookingEntity hotelBooking = new HotelBookingEntity();
        hotelBooking.setCode(GeneratorCM.bookingCode(booking == null ? null : booking.getCode()));
        hotelBooking.setStatus(BookingConstantPayment.PENDING);
        hotelBooking.setCheckIn(DatetimeUtil.toDate(checkRateRS.getSummary().getCheckIn()));
        hotelBooking.setCheckOut(DatetimeUtil.toDate(checkRateRS.getSummary().getCheckOut()));
        hotelBooking.setPaymentFeePercentage(checkRateRS.getSummary().getMarkupPaymentAmount());
        hotelBooking.setMarkupPayAmount(checkRateRS.getSummary().getMarkupPaymentAmount());
        hotelBooking.setMarkupPayPercentage(checkRateRS.getSummary().getMarkupPaymentPercentage());
        hotelBooking.setPaymentFeeAmount(checkRateRS.getSummary().getMarkupPaymentPercentage());
        hotelBooking.setMarkupAmount(checkRateRS.getSummary().getMarkupAmount());
        hotelBooking.setTotalAmount(checkRateRS.getSummary().getTotalAmount());
        hotelBooking.setCost(checkRateRS.getSummary().getCost());
        hotelBooking.setBookingReference(checkRateRS.getCode());
        // TODO:set commissionAmount and subtotal
//        hotelBooking.setCommisionAmount(checkRateRS.getHotel().getCommissionAmount());
        //        hotelBooking.setTotalAmountBeforeDiscount();

        HolderDSRQ holder = checkRateRS.getHolder();
        if (holder != null) {
            hotelBooking.setContactEmail(holder.getEmail());
            hotelBooking.setContactFullname(holder.getName());
            hotelBooking.setContactPhone(holder.getPhoneNumber());
            hotelBooking.setContactPhoneCode(holder.getPhoneCode());
        }

        hotelBooking.setCurrencyCode(checkRateRS.getSummary().getCurrencyCode());
        hotelBooking.setStakeholderUserId(jwtUtils.userToken().getSkyuserId());
        hotelBooking.setStakeholderCompanyId(headerCM.getCompanyId());

        BigDecimal totalAmountBeforeDiscount = checkRateRS.getSummary().getRooms()
                .stream()
                .map(room -> room.getRates()
                        .stream()
                        .map(Rate::getSubTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        hotelBooking.setTotalAmountBeforeDiscount(totalAmountBeforeDiscount);

        HotelBookingEntity saveBooking = bookingRP.save(hotelBooking);

        storeBookingRate(saveBooking.getId(), hotelBooking.getCode(), checkRateRS.getHotel().getCode(), checkRateRS.getSummary().getRooms());

        checkRateRS.setCode(saveBooking.getCode());
    }

    private void storeBookingRate(Long bookingId, String bookingCode, Integer hotelCode, List<Room> rooms) {

        rooms.forEach(room -> {
            room.getRates().forEach(rate -> {
                HotelBookingRateEntity bookingRate = new HotelBookingRateEntity();
                bookingRate.setBookingId(bookingId);
                bookingRate.setTotalAmount(rate.getTotalAmount());
                bookingRate.setMarkupAmount(rate.getMarkupAmount());
                bookingRate.setMarkupPercentage(rate.getMarkupPercentage());

                var lastRate = bookingCode.substring(4);

                String rateCode = GeneratorCM
                    .rateCode(hotelCode, room.getCode(), rate.getBoardCode(), lastRate);

                bookingRate.setRateCode(rateCode);
                bookingRate.setTotalRoom(rate.getTotalRoom());
                hoteRate.save(bookingRate);
            });
        });

    }

    @Override
    public void confirmBooking(HotelBookingEntity bookingEntity, PaymentTransactionRQ paymentTransactionRQ) {

        BookingConfirmationHBRQ bookingConfirmationHBRQ = new BookingConfirmationHBRQ();
        bookingConfirmationHBRQ.setCode(bookingEntity.getBookingReference());

        CheckRateCached checkRateCached = bookingCachedSV.retrieve(bookingEntity.getCode());

        HolderHBRQ holderHBRQ = modelMapper.map(checkRateCached.getHolder(), HolderHBRQ.class);
        bookingConfirmationHBRQ.setHolder(holderHBRQ);

        PaymentInfoHBRQ paymentInfoHBRQ = modelMapper.map(paymentTransactionRQ, PaymentInfoHBRQ.class);
        paymentInfoHBRQ.setCardHolderName(paymentTransactionRQ.getHolderName());
        paymentInfoHBRQ.setRemark(paymentTransactionRQ.getDescription());
        paymentInfoHBRQ.setRemark(paymentTransactionRQ.getDescription());

        bookingConfirmationHBRQ.setPaymentInfo(paymentInfoHBRQ);

        List<BookingConfirmationRoomHBRQ> collect = checkRateCached.getRooms()
            .stream()
            .map(bookingRateCached -> IntStream.range(0, bookingRateCached.getRooms())
                .mapToObj(i -> {
                    BookingConfirmationRoomHBRQ map = modelMapper.map(bookingRateCached, BookingConfirmationRoomHBRQ.class);
                    map.setPaxes(null);
                    return map;
                })
                .collect(Collectors.toList()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        bookingConfirmationHBRQ.setRooms(collect);

        try {

            Mono<BookingConfirmationHBRS> bookingMono = bookingAction.confirmBooking(bookingConfirmationHBRQ);
            var data = bookingMono.block();
            bookingEntity.setReference(data.getBooking().getReference());
        } catch (WebClientResponseException exception) {
            if (exception instanceof WebClientResponseException.InternalServerError) {
                System.out.println("Booking Response ==> " + exception.getResponseBodyAsString());
                //TODO: Booking Failed and Refund here
                bookingEntity.setStatus(BookingStatus.FAILED.name());
                bookingRP.save(bookingEntity);
            }
            return;
        }

        // Update booking status
        bookingEntity.setStatus(BookingStatus.CONFIRMED.name());
        bookingRP.save(bookingEntity);
    }

}
