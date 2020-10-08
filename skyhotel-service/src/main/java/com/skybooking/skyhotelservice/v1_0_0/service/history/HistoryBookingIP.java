package com.skybooking.skyhotelservice.v1_0_0.service.history;

import com.skybooking.skyhotelservice.constant.AttachmentConstant;
import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.booking.HistoryAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.HotelsRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.history.HistoryHBRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.history.HistoryHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hoteldata.BasicHotelDataDSRS;
import com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history.HistoryBookingNQ;
import com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history.HistoryBookingTO;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.history.HistoryBookingRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.BookingDetailRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.HistoryBookingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.HistoryListRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.HistoryBookingDetailRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.RoomDetail;
import com.skybooking.skyhotelservice.v1_0_0.util.GeneralUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.aws.AwsPartCM;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private GeneralUtil generalUtil;

    @Autowired
    private AwsPartCM awsPartCM;

    public StructureRS listing() {

        HistoryBookingRQ historyBookingRQ = new HistoryBookingRQ(request);

        Page<HistoryBookingTO> historyBookingTO = historyBookingNQ.historyBooking(
                headerCM.getCompanyIdZ(),
                jwt.userToken().getSkyuserId(),
                headerCM.skyType(),
                PageRequest.of(historyBookingRQ.getPage(), historyBookingRQ.getSize()));

        List<HistoryBookingRS> historyBookingRS = historyBookingTO.stream()
                .map(historybooking -> modelMapper.map(historybooking, HistoryBookingRS.class))
                .collect(Collectors.toList());

        if (historyBookingRS.size() > 0) {
            List<String> bookingReference = historyBookingRS
                    .stream()
                    .map(HistoryBookingRS::getBookingReference)
                    .distinct()
                    .collect(Collectors.toList());

            HistoryHBRS historyHBRS = historyAction.listHistory(new HistoryHBRQ(bookingReference));
            if (historyHBRS != null) {
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
                bookingDataHBList.forEach(bookingDataHB -> {

                    historyBookingRS
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
                                            .divide(BigDecimal.valueOf(historyBooking.getTotalRoom()))
                                            .divide(BigDecimal.valueOf(historyBooking.getNights())));
                                }

                                return historyBooking;

                            })
                            .peek(historyBooking -> {
                                historyBooking.setCheckIn(DatetimeUtil.convertDateTimeISO(DatetimeUtil.toDate(historyBooking.getCheckIn())));
                                historyBooking.setCheckOut(DatetimeUtil.convertDateTimeISO(DatetimeUtil.toDate(historyBooking.getCheckOut())));
                            }).collect(Collectors.toList());
                });
            }

        }

        PagingRS paging = new PagingRS(historyBookingRQ.getPage() + 1, historyBookingRQ.getSize(), (int) historyBookingTO.getTotalElements());

        return responseBodyWithSuccessMessage(historyBookingRS, paging);

    }

    public HistoryBookingDetailRS historyDetail(String bookingCode) {
        var bookingDetailRS =  detail(bookingCode, headerCM.skyType());
        HistoryBookingDetailRS data = modelMapper.map(bookingDetailRS, HistoryBookingDetailRS.class);
        return data;
    }

    public BookingDetailRS detail(String bookingCode, String skyType) {

        Optional<HistoryBookingTO> historyBookingTO = historyBookingNQ.historyBookingDetail(headerCM.getCompanyIdZ(),
                jwt.userToken().getSkyuserId() != null ? jwt.userToken().getSkyuserId() : 0,
                bookingCode,
                skyType);

        BookingDetailRS historyBookingRS = new BookingDetailRS();

        historyBookingTO.ifPresent(historyBooking -> {

            BeanUtils.copyProperties(historyBooking, historyBookingRS);

            List<String> bookingReference = Collections.singletonList(historyBookingRS.getBookingReference());

            HistoryHBRS historyHBRS = historyAction.listHistory(new HistoryHBRQ(bookingReference));

            if (historyHBRS != null) {

                Optional<HistoryListRS> bookingDataHB = historyHBRS.getData().stream().findFirst();
                bookingDataHB.ifPresent( dataHB -> {
                    historyBookingRS.setBookingDate(dataHB.getBookingDate());
                    List<Integer> hotelCode = Collections.singletonList(dataHB.getHotelCode());
                    Optional<BasicHotelDataDSRS> bookingContent = historyAction.historyContent(new HotelsRQDS(hotelCode)).getData().stream().findFirst();

                    bookingContent.ifPresent(bookingContentData -> {
                        historyBookingRS.setHotelName(bookingContentData.getHotelName());
                        historyBookingRS.setHotelLocation(bookingContentData.getDestination());
                    });

                    List<RoomDetail> roomList = new ArrayList<>();
                    AtomicReference<Integer> totalRooms = new AtomicReference<>(0);

                    dataHB.getRooms().forEach(room -> {
                        RoomDetail roomDetail = new RoomDetail();

                        roomDetail.setCode(room.getCode());
                        roomDetail.setType(null);//Not yet
                        roomDetail.setSpecialRequest(null);//Not yet
                        roomDetail.setThumbnail(null);//Not yet
                        roomDetail.setCapacity(null);//Not yet

                        room.getRates().forEach(rate -> {
                            totalRooms.updateAndGet(v -> v + rate.getTotalRoom());
                        });
                        roomList.add(roomDetail);
                    });

                    historyBookingRS.setRoom(roomList
                            .stream()
                            .filter(generalUtil.distinctByKey(r -> r.getCode())).collect(Collectors.toList()));

                    historyBookingRS.setTotalRoom(totalRooms.get());

                });
            }

            Long nights = DatetimeUtil.night(historyBookingRS.getCheckIn().toString(), historyBookingRS.getCheckOut().toString());
            historyBookingRS.setNights(nights);

            historyBookingRS.setCancelationPolicy(CancellationTypeConstant.NON_REFUNDABLE.getValue());//Not Yet
        });

        return historyBookingRS;

    }

}
