package com.skybooking.skyhotelservice.v1_0_0.service.history;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.BookingDetailRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.HistoryBookingDetailRS;
import org.springframework.stereotype.Service;

@Service
public interface HistoryBookingSV {
    StructureRS listing();
    BookingDetailRS detail(String bookingCode, String userType);
    HistoryBookingDetailRS historyDetail(String bookingCode);
}
