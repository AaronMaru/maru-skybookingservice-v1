package com.skybooking.skyhotelservice.v1_0_0.service.history;

import com.skybooking.skyhotelservice.constant.AttachmentConstant;
import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.booking.HistoryAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.HotelsRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.history.FilterRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.history.HistoryHBRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.history.HistoryHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hoteldata.BasicHotelDataDSRS;
import com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history.HistoryBookingNQ;
import com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history.HistoryBookingTO;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.history.HistoryBookingRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.HistoryBookingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.HistoryListRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.aws.AwsPartCM;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class HistoryBookingIP extends BaseServiceIP implements HistoryBookingSV {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HistoryBookingNQ historyBookingNQ;

    @Autowired
    private HistoryAction historyAction;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private AwsPartCM awsPartCM;


    public StructureRS listing() {

        HistoryBookingRQ historyBookingRQ = new HistoryBookingRQ(request, jwt.userToken());
        historyBookingRQ.setAction(checkSearchOrFilter());

        List<HistoryBookingTO> historyBookingTO = historyBookingNQ.historyBooking(
                historyBookingRQ.getCompanyId(),
                historyBookingRQ.getSkyuserId(),
                historyBookingRQ.getKeywordSearch(),
                historyBookingRQ.getBookedBy(),
                historyBookingRQ.getCheckIn(),
                historyBookingRQ.getCheckOut(),
                historyBookingRQ.getRoomNumber(),
                historyBookingRQ.getBookingStatus(),
                historyBookingRQ.getPaymentType(),
                historyBookingRQ.getPriceRangeStart(),
                historyBookingRQ.getPriceRangeEnd(),
                historyBookingRQ.getAction(),
                historyBookingRQ.getDefaultStatus(),
                headerCM.skyType());

        List<HistoryBookingRS> historyBookingRS = new ArrayList<>();
        PagingRS paging = new PagingRS();
        System.out.println(historyBookingTO);
        if (historyBookingTO.size() > 0) {

            List<String> bookingReference = historyBookingTO
                    .stream()
                    .map(HistoryBookingTO::getBookingReference)
                    .distinct()
                    .collect(Collectors.toList());

            HistoryHBRS historyHBRS = historyAction.listHistory(new HistoryHBRQ(bookingReference, new FilterRQ(historyBookingRQ)));

            if (historyHBRS != null && historyHBRS.getData().size() > 0) {
                List<String> bookingCodeHS = historyHBRS.getData()
                        .stream()
                        .map(HistoryListRS::getBookingCode)
                        .distinct()
                        .collect(Collectors.toList());

                historyBookingRS = historyBookingTO.stream()
                        .filter(c -> bookingCodeHS.contains(c.getBookingReference()))
                        .map(historybooking -> modelMapper.map(historybooking, HistoryBookingRS.class))
                        .collect(Collectors.toList());

                BeanUtils.copyProperties(historyHBRS.getPaging(), paging);

            }

            if (historyBookingRS.size() > 0) {
                List<HistoryListRS> bookingDataHBList = historyHBRS.getData();

                List<Integer> hotelCode = bookingDataHBList
                        .stream()
                        .map(HistoryListRS::getHotelCode)
                        .distinct()
                        .collect(Collectors.toList());

                List<BasicHotelDataDSRS> bookingContentData = historyAction.historyContent(new HotelsRQDS(hotelCode)).getData();

                /**
                 * Map Data
                 */
                List<HistoryBookingRS> finalHistoryBookingRS = historyBookingRS;
                bookingDataHBList.forEach( bookingDataHB -> {
                    finalHistoryBookingRS
                            .stream()
                            .filter(f -> f.getBookingReference().equals(bookingDataHB.getBookingCode()))
                            .map(historyBooking -> {
                                Long nights = DatetimeUtil.night(historyBooking.getCheckIn(), historyBooking.getCheckOut());
                                historyBooking.setNights(nights);

                                if (bookingDataHB.getBookingDate() != null) {
                                    historyBooking.setBookingDate(DatetimeUtil.convertDateTimeISO(bookingDataHB.getBookingDate()));
                                }

                                Optional<BasicHotelDataDSRS> content = bookingContentData
                                        .stream()
                                        .filter(bookingContent -> bookingContent.getCode().equals(bookingDataHB.getHotelCode()))
                                        .findFirst();

                                content.ifPresent(contentData -> {
                                    historyBooking.setHotelName(contentData.getHotelName());
                                    historyBooking.setHotelLocation(contentData.getDestination());
                                    historyBooking.setInterestPointDistance(contentData.getInterestPointDistance());
                                    historyBooking.setInterestPointDistanceUnit(contentData.getInterestPointDistanceUnit());
                                    historyBooking.setInterestPointName(contentData.getInterestPointName());
                                    historyBooking.setThumbnail(awsPartCM.partUrl(AttachmentConstant.HOTEL_MEDIUM, contentData.getThumbnail()));
                                });

                                if (bookingDataHB.getRooms().size() > 0) {

                                    AtomicReference<Integer> totalRooms = new AtomicReference<>(0);
                                    AtomicReference<Integer> totalAdults = new AtomicReference<>(0);
                                    bookingDataHB.getRooms().forEach(room -> {
                                        room.getRates().forEach(rate -> {
                                            totalRooms.updateAndGet(v -> v + rate.getTotalRoom());
                                            totalAdults.updateAndGet(v -> v + rate.getTotalAdult());
                                        });
                                    });

                                    historyBooking.setTotalRoom(totalRooms.get());
                                    historyBooking.setTotalAdult(totalAdults.get());

                                    historyBooking.setUnitAmount(historyBooking.getTotalAmount()
                                            .divide(BigDecimal.valueOf(historyBooking.getTotalRoom()), 2, RoundingMode.HALF_UP)
                                            .divide(BigDecimal.valueOf(historyBooking.getNights()), 2, RoundingMode.HALF_UP));
                                }

                                return historyBooking;

                            })
                            .peek(historyBooking -> {
                                historyBooking.setCheckIn(DatetimeUtil.convertDateTimeISO(DatetimeUtil.toDate(historyBooking.getCheckIn())));
                                historyBooking.setCheckOut(DatetimeUtil.convertDateTimeISO(DatetimeUtil.toDate(historyBooking.getCheckOut())));
                            }).collect(Collectors.toList());
                });
                modelMapper.map(finalHistoryBookingRS, historyBookingRS);
            }
        }

        return responseBodyWithSuccessMessage(historyBookingRS, paging);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Determine is search or filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     * @Param booking
     */
    public String checkSearchOrFilter() {

        if (request.getParameter("bookedBy") != null || request.getParameter("checkIn") != null ||
                request.getParameter("checkOut") != null || request.getParameter("roomNumber") != null ||
                request.getParameter("bookingStatus") != null || request.getParameter("bookingDateFrom") != null ||
                request.getParameter("bookingDateTo") != null || request.getParameter("paymentType") != null ||
                request.getParameter("priceRangeStart") != null || request.getParameter("priceRangeEnd") != null ||
                request.getParameter("city") != null || request.getParameter("boardName") != null
        ) {

            return "FILTER";

        } else if(request.getParameter("keywordSearch") != null && !request.getParameter("keywordSearch").equals("")) {

            return "SEARCH";

        }

        return "OTHER";

    }


    public HistoryBookingDetailRS historyDetail(String bookingCode) {
        var bookingDetailRS =  detail(bookingCode, headerCM.skyType(), headerCM.getCompanyIdZ());
        HistoryBookingDetailRS data = modelMapper.map(bookingDetailRS, HistoryBookingDetailRS.class);
        return data;
    }

    public BookingDetailRS detail(String bookingCode, String skyType, Long companyId) {

        Optional<HistoryBookingTO> historyBookingTO = historyBookingNQ.historyBookingDetail(companyId,
                jwt.userToken().getSkyuserId() != null ? jwt.userToken().getSkyuserId() : 0,
                bookingCode,
                skyType);

        BookingDetailRS historyBookingRS = new BookingDetailRS();
        PriceInfo priceInfo = new PriceInfo();
        PaymentInfo paymentInfo = new PaymentInfo();
        ContactInfo contactInfo = new ContactInfo();
        HotelInfo hotelInfo = new HotelInfo();

        historyBookingTO.ifPresent(historyBooking -> {

            BeanUtils.copyProperties(historyBooking, historyBookingRS);
            BeanUtils.copyProperties(historyBooking, priceInfo);
            BeanUtils.copyProperties(historyBooking, contactInfo);
            BeanUtils.copyProperties(historyBooking, paymentInfo);

            BigDecimal discountAmount = priceInfo.getTotalAmountBeforeDiscount().subtract(priceInfo.getTotalAmount());
            BigDecimal discountPercentage = CalculatePriceUtil.calculateDiscountPercentage(priceInfo.getTotalAmount(), discountAmount);
            priceInfo.setDiscountPercentage(discountPercentage);
            priceInfo.setDiscountAmount(discountAmount);

            List<String> bookingReference = Collections.singletonList(historyBookingRS.getBookingReference());

            HistoryHBRS historyHBRS = historyAction.listHistory(new HistoryHBRQ(bookingReference));
            historyBookingRS.setCheckIn(DatetimeUtil.convertDateTimeISO(historyBooking.getCheckIn()));
            historyBookingRS.setCheckOut(DatetimeUtil.convertDateTimeISO(historyBooking.getCheckOut()));

            if (historyHBRS != null) {

                Optional<HistoryListRS> bookingDataHB = historyHBRS.getData().stream().findFirst();
                bookingDataHB.ifPresent( dataHB -> {
                    if (dataHB.getBookingDate() != null) {
                        historyBookingRS.setBookingDate(DatetimeUtil.convertDateTimeISO(dataHB.getBookingDate()));
                    }
                    List<Integer> hotelCode = Collections.singletonList(dataHB.getHotelCode());
                    Optional<BasicHotelDataDSRS> bookingContent = historyAction.historyContent(new HotelsRQDS(hotelCode)).getData().stream().findFirst();

                    AtomicReference<Integer> totalRooms = new AtomicReference<>(0);
                    List<RoomDetail> roomList = new ArrayList<>();

                    dataHB.getRooms().forEach(room -> {
                        RoomDetail roomDetail = new RoomDetail();
//                        roomDetail.setSpecialRequest();
                        room.getRates().forEach(rate -> {
                            totalRooms.updateAndGet(v -> v + rate.getTotalRoom());
                        });

                        var checkExist = roomList.stream().filter(f -> f.getCode().equals(room.getCode())).findFirst();
                        if (!checkExist.isPresent()) {
                            roomDetail.setCode(room.getCode());
                            roomList.add(roomDetail);
                        }

                    });
                    historyBookingRS.setTotalRoom(totalRooms.get());

                    bookingContent.ifPresent(bookingContentData -> {

                        hotelInfo.setHotelName(bookingContentData.getHotelName());
                        hotelInfo.setHotelLocation(bookingContentData.getDestination());
                        hotelInfo.setInterestPointName(bookingContentData.getInterestPointName());
                        hotelInfo.setInterestPointDistance(bookingContentData.getInterestPointDistance());
                        hotelInfo.setInterestPointDistanceUnit(bookingContentData.getInterestPointDistanceUnit());

                        roomList.stream()
                                .peek(room -> {
                                    var roomContent = bookingContentData.getRooms()
                                            .stream()
                                            .filter(r -> r.getCode().equals(room.getCode()))
                                            .findFirst();
                                    roomContent.ifPresent(r -> {
                                            room.setDescription(r.getDescription());
                                            room.setCode(r.getCode());
                                            room.setCapacity(r.getCapacity());
                                            room.setThumbnail(awsPartCM.partUrl(AttachmentConstant.HOTEL_MEDIUM, r.getThumbnail()));
                            });
                        }).collect(Collectors.toList());

                    });
                    historyBookingRS.setRoom(roomList);
                });
            }

            Long nights = DatetimeUtil.night(historyBookingRS.getCheckIn(), historyBookingRS.getCheckOut());
            historyBookingRS.setNights(nights);

            historyBookingRS.setCancelationPolicy(CancellationTypeConstant.NON_REFUNDABLE.getValue());//Not Yet

        });

        historyBookingRS.setPriceInfo(priceInfo);
        historyBookingRS.setPaymentInfo(paymentInfo);
        historyBookingRS.setHotelInfo(hotelInfo);
        historyBookingRS.setContactInfo(contactInfo);

        return historyBookingRS;

    }

}
