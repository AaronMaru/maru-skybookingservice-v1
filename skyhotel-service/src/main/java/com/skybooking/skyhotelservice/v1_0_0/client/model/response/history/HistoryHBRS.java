package com.skybooking.skyhotelservice.v1_0_0.client.model.response.history;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.HistoryListRS;
import lombok.Data;
import java.util.List;

@Data
public class HistoryHBRS {
    private List<HistoryListRS> data;
}
