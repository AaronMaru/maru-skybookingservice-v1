package com.skybooking.skyhotelservice.v1_0_0.util.mapper;

import com.skybooking.skyhotelservice.constant.PaymentTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RatePromotionRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RateTaxRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room.RoomRateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.PromotionRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.RateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.facility.HotelFacilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.RateTaxesHBRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.BoardRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility.HotelFacilityRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HotelConverterMapper {

    HotelFacilityRS toHotelFacilityRS(HotelFacilityRSDS hotelFacilityRSDS);

    RoomRS toRoomRS(RoomRSDS roomRSDS);

    RateRSDS toRateRSDS(RoomRateRSDS roomRateRSDS);

    PromotionRSDS toPromotionRSDS(RatePromotionRSDS ratePromotionRSDS);

    @Mapping(source = "rateRSDS.paymentType", target = "paymentType", qualifiedByName = "paymentTypeToEnum")
    RateRS toRateRS(RateRSDS rateRSDS);

    SegmentRS toSegmentRS(SegmentRSDS segmentRSDS);

    ImageRS toImageRS(ImageRSDS imageRSDS);

    InterestPointRS toInterestPointRS(InterestPointRSDS interestPointRSDS);

    PhoneRS toPhoneRS(PhoneRSDS phoneRSDS);

    BoardRS toBoardRS(BoardRSDS boardRSDS);

    LocationRS toLocationRS(LocationRSDS locationRSDS);

    BasicRS toBasicRS(BasicRSDS basicRSDS);

    RateTaxesHBRS toRateTaxesHBRS(RateTaxRSDS rateTaxRSDS);

    @Named("paymentTypeToEnum")
    static RateRS.PaymentType paymentTypeToEnum(String paymentType) {
        if (paymentType.equals(RateRS.PaymentType.PREPAID.getValue()) || PaymentTypeConstant.PREPAY.equals(paymentType))
            return RateRS.PaymentType.PREPAID;

        if (paymentType.equals(RateRS.PaymentType.POSTPAID.getValue()) || PaymentTypeConstant.POSTPAY.equals(paymentType))
            return RateRS.PaymentType.POSTPAID;

        return null;
    }
}
