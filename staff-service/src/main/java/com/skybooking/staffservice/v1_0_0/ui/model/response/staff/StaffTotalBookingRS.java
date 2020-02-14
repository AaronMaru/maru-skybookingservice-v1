package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

public class StaffTotalBookingRS {

    private AmountRS upcoming;
    private AmountRS completed;
    private AmountRS cancellation;

    public AmountRS getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(AmountRS upcoming) {
        this.upcoming = upcoming;
    }

    public AmountRS getCompleted() {
        return completed;
    }

    public void setCompleted(AmountRS completed) {
        this.completed = completed;
    }

    public AmountRS getCancellation() {
        return cancellation;
    }

    public void setCancellation(AmountRS cancellation) {
        this.cancellation = cancellation;
    }
}
